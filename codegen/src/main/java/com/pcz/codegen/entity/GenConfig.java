package com.pcz.codegen.entity;

import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class GenConfig {
    /**
     * 请求参数
     */
    private TableRequest request;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 作者
     */
    private String author;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;
}
