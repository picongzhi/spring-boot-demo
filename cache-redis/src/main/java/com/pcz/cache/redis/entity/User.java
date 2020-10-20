package com.pcz.cache.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author picongzhi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -8873642389343918803L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;
}
