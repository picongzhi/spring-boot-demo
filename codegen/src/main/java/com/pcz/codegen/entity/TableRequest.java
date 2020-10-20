package com.pcz.codegen.entity;

import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class TableRequest {
    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页数
     */
    private Integer pageSize;

    /**
     * jdbc前缀
     */
    private String prepend;

    /**
     * jdbc url
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 表名
     */
    private String tableName;
}
