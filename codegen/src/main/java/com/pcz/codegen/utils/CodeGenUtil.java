package com.pcz.codegen.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.poi.word.WordUtil;
import cn.hutool.setting.dialect.Props;
import com.google.common.collect.Lists;
import com.pcz.codegen.constants.GenConstants;
import com.pcz.codegen.entity.ColumnEntity;
import com.pcz.codegen.entity.GenConfig;
import com.pcz.codegen.entity.TableEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author picongzhi
 */
@Slf4j
@UtilityClass
public class CodeGenUtil {
    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";
    private final String API_JS_VM = "api.js.vm";

    private List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/api.js.vm");

        return templates;
    }

    /**
     * 生成代码
     *
     * @param genConfig 配置信息
     * @param table     表信息
     * @param columns   列信息
     * @param zip       zip输出流
     */
    public void generateCode(GenConfig genConfig, Entity table, List<Entity> columns, ZipOutputStream zip) {
        Props props = getConfig();

        boolean hasBigDecimal = false;
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getTableName());
        if (StrUtil.isNotBlank(genConfig.getComments())) {
            tableEntity.setComments(genConfig.getComments());
        } else {
            tableEntity.setComments(table.getStr("tableComment"));
        }

        String tablePrefix;
        if (StrUtil.isNotBlank(genConfig.getTablePrefix())) {
            tablePrefix = genConfig.getTablePrefix();
        } else {
            tablePrefix = props.getStr("tablePrefix");
        }

        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StrUtil.lowerFirst(className));

        List<ColumnEntity> columnEntityList = Lists.newArrayList();
        for (Entity column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.getStr("columnName"));
            columnEntity.setDataType(column.getStr("dataType"));
            columnEntity.setComments(column.getStr("columnComment"));
            columnEntity.setExtra(column.getStr("extra"));

            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StrUtil.lowerFirst(attrName));

            String attrType = props.getStr(columnEntity.getDataType(), "unknownType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }

            if ("PRI".equalsIgnoreCase(column.getStr("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columnEntityList.add(columnEntity);
        }
        tableEntity.setColumns(columnEntityList);

        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        Properties properties = new Properties();
        properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);

        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());
        map.put("year", DateUtil.year(new Date()));

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            map.put("comments", genConfig.getComments());
        } else {
            map.put("comments", tableEntity.getComments());
        }

        if (StrUtil.isNotBlank(genConfig.getAuthor())) {
            map.put("author", genConfig.getAuthor());
        } else {
            map.put("author", props.getStr("author"));
        }

        if (StrUtil.isNotBlank(genConfig.getModuleName())) {
            map.put("moduleName", genConfig.getModuleName());
        } else {
            map.put("moduleName", props.getStr("moduleName"));
        }

        if (StrUtil.isNotBlank(genConfig.getPackageName())) {
            map.put("package", genConfig.getPackageName());
            map.put("mainPath", genConfig.getPackageName());
        } else {
            map.put("package", props.getStr("package"));
            map.put("mainPath", props.getStr("mainPath"));
        }

        VelocityContext context = new VelocityContext(map);

        List<String> templates = getTemplates();
        for (String template : templates) {
            StringWriter stringWriter = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, stringWriter);

            try {
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(
                        template,
                        tableEntity.getCaseClassName(),
                        map.get("package").toString(),
                        map.get("moduleName").toString()))));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, stringWriter.toString());
                IoUtil.close(stringWriter);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名: " + tableEntity.getTableName(), e);
            }
        }
    }

    /**
     * 获取配置信息
     *
     * @return 配置信息
     */
    private Props getConfig() {
        Props props = new Props("generator.properties");
        props.autoLoad(true);

        return props;
    }

    /**
     * 列名转换成Java属性名
     *
     * @param columnName 列名
     * @return 属性名
     */
    private String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'})
                .replace("_", "");
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName   表名
     * @param tablePrefix 表前缀
     * @return 类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StrUtil.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     *
     * @param template    模板
     * @param className   类名
     * @param packageName 包名
     * @param moduleName  模块名
     * @return 文件名
     */
    private String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = GenConstants.SIGNATURE + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        String resourcePath = GenConstants.SIGNATURE + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
        String apiPath = GenConstants.SIGNATURE + File.separator + "api" + File.separator;

        if (StrUtil.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }

        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return resourcePath + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (template.contains(API_JS_VM)) {
            return apiPath + className.toLowerCase() + ".js";
        }

        return null;
    }
}
