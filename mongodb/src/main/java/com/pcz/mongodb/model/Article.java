package com.pcz.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author picongzhi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 点赞数
     */
    private Long thumbUp;

    /**
     * 访客数
     */
    private Long visits;
}
