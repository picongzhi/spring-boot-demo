package com.pcz.cache.ehcache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author picongzhi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1358978546190041176L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;
}
