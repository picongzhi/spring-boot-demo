package com.pcz.mongodb.repository;

import com.pcz.mongodb.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author picongzhi
 */
public interface ArticleRepository extends MongoRepository<Article, Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @return List<Article>
     */
    List<Article> findByTitleLike(String title);
}
