package com.pcz.orm.jdbc.template.dao.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.pcz.orm.jdbc.template.annotation.Column;
import com.pcz.orm.jdbc.template.annotation.Ignore;
import com.pcz.orm.jdbc.template.annotation.Pk;
import com.pcz.orm.jdbc.template.annotation.Table;
import com.pcz.orm.jdbc.template.constant.Const;
import javafx.scene.chart.ValueAxis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author picongzhi
 */
@Slf4j
public class BaseDao<T, P> {
    private JdbcTemplate jdbcTemplate;
    private Class<T> clazz;

    @SuppressWarnings(value = "unchecked")
    public BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 通用插入，自增列需要添加注解
     *
     * @param t          对象
     * @param ignoreNull 是否忽略Null值
     * @return 影响行数
     */
    protected Integer insert(T t, Boolean ignoreNull) {
        String table = getTableName(t);
        List<Field> filterField = getField(t, ignoreNull);
        List<String> columnList = getColumns(filterField);

        String columns = StrUtil.join(Const.SEPARATOR_COMMA, columnList);
        String params = StrUtil.repeatAndJoin("?", columnList.size(), Const.SEPARATOR_COMMA);
        Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).toArray();

        String sql = StrUtil.format("INSERT INTO {table} ({columns}) VALUES ({params})",
                Dict.create().set("table", table).set("columns", columns).set("params", params));
        log.debug("[执行SQL] SQL: {}", sql);
        log.debug("[执行SQL] 参数: {}", JSONUtil.toJsonStr(values));

        return jdbcTemplate.update(sql, values);
    }

    /**
     * 通用根据主键删除
     *
     * @param pk 主键
     * @return 影响行数
     */
    protected Integer deleteById(P pk) {
        String tableName = getTableName();
        String sql = StrUtil.format("DELETE FROM {table} WHERE id = ?",
                Dict.create().set("table", tableName));
        log.debug("[执行SQL] SQL: {}", sql);
        log.debug("[执行SQL] 参数: {}", JSONUtil.toJsonStr(pk));

        return jdbcTemplate.update(sql, pk);
    }

    /**
     * 通用根据主键更新，自增列需要添加注解
     *
     * @param t          对象
     * @param pk         主键
     * @param ignoreNull 是否忽略Null值
     * @return 影响行数
     */
    protected Integer updateById(T t, P pk, Boolean ignoreNull) {
        String tableName = getTableName(t);
        List<Field> filterField = getField(t, ignoreNull);
        List<String> columnList = getColumns(filterField);

        List<String> columns = columnList.stream().map(s -> StrUtil.appendIfMissing(s, " = ?")).collect(Collectors.toList());
        String params = StrUtil.join(Const.SEPARATOR_COMMA, columns);

        List<Object> valueList = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).collect(Collectors.toList());
        valueList.add(pk);
        Object[] values = ArrayUtil.toArray(valueList, Object.class);

        String sql = StrUtil.format("UPDATE {table} SET {params} WHERE id = ?",
                Dict.create().set("table", tableName).set("params", params));
        log.debug("[执行SQL] SQL: {}", sql);
        log.debug("[执行SQL] 参数: {}", JSONUtil.toJsonStr(values));

        return jdbcTemplate.update(sql, values);
    }

    /**
     * 通用根据主键查询单条记录
     *
     * @param pk 主键
     * @return 记录
     */
    public T findOneById(P pk) {
        String tableName = getTableName();
        String sql = StrUtil.format("SELECT * FROM {table} WHERE id = ?",
                Dict.create().set("table", tableName));
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);

        log.debug("[执行SQL] SQL: {}", sql);
        log.debug("[执行SQL] 参数: {}", JSONUtil.toJsonStr(pk));

        return jdbcTemplate.queryForObject(sql, new Object[]{pk}, rowMapper);
    }

    public List<T> findByExample(T t) {
        String tableName = getTableName(t);
        List<Field> filterField = getField(t, null);
        List<String> columnList = getColumns(filterField);
        List<String> columns = columnList.stream().map(s -> " and " + s + " = ? ").collect(Collectors.toList());

        String where = StrUtil.join(" ", columns);
        Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).toArray();

        String sql = StrUtil.format("SELECT * FROM {table} WHERE 1=1 {where}",
                Dict.create().set("table", tableName).set("where", StrUtil.isBlank(where) ? "" : where));
        log.debug("[执行SQL] SQL: {}", sql);
        log.debug("[执行SQL] 参数: {}", JSONUtil.toJsonStr(values));

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, values);
        List<T> ret = CollUtil.newArrayList();
        maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, ReflectUtil.newInstance(clazz), false, false)));

        return ret;
    }

    /**
     * 获取表名
     *
     * @param t 对象
     * @return 表名
     */
    private String getTableName(T t) {
        Table tableAnnotation = t.getClass().getAnnotation(Table.class);
        if (ObjectUtil.isNotNull(tableAnnotation)) {
            return StrUtil.format("`{}`", tableAnnotation.name());
        } else {
            return StrUtil.format("`{}`", t.getClass().getName().toLowerCase());
        }
    }

    /**
     * 获取表名
     *
     * @return 表名
     */
    private String getTableName() {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (ObjectUtil.isNotNull(tableAnnotation)) {
            return StrUtil.format("`{}`", tableAnnotation.name());
        } else {
            return StrUtil.format("`{}`", clazz.getName().toLowerCase());
        }
    }

    /**
     * 获取列
     *
     * @param fieldList 字段列表
     * @return 列信息列表
     */
    private List<String> getColumns(List<Field> fieldList) {
        List<String> columnList = CollUtil.newArrayList();
        for (Field field : fieldList) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName;
            if (ObjectUtil.isNotNull(columnAnnotation)) {
                columnName = columnAnnotation.name();
            } else {
                columnName = field.getName();
            }

            columnList.add(StrUtil.format("`{}`", columnName));
        }

        return columnList;
    }

    /**
     * 获取字段列表(过滤数据库中不存在的字段以及自增列)
     *
     * @param t          对象
     * @param ignoreNull 是否忽略空值
     * @return 字段列表
     */
    private List<Field> getField(T t, Boolean ignoreNull) {
        // 获取所有字段，包括父类中的字段
        Field[] fields = ReflectUtil.getFields(t.getClass());

        // 过滤数据库中不存在的字段，以及自增列
        List<Field> filterField;
        Stream<Field> fieldStream = CollUtil.toList(fields).stream().filter(field -> ObjectUtil.isNull(field.getAnnotation(Ignore.class)) || ObjectUtil.isNull(field.getAnnotation(Pk.class)));

        // 是否过滤字段值为null的字段
        if (ignoreNull) {
            filterField = fieldStream.filter(field -> ObjectUtil.isNotNull(ReflectUtil.getFieldValue(t, field))).collect(Collectors.toList());
        } else {
            filterField = fieldStream.collect(Collectors.toList());
        }

        return filterField;
    }
}
