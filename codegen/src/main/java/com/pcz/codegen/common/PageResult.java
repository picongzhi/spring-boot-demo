package com.pcz.codegen.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总数
     */
    private Long total;

    /**
     * 页码
     */
    private int pageNumber;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 结果集
     */
    private List<T> list;
}
