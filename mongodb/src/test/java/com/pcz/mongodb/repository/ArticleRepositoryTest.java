package com.pcz.mongodb.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.pcz.mongodb.SpringBootMongodbApplicationTest;
import com.pcz.mongodb.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ArticleRepositoryTest extends SpringBootMongodbApplicationTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Snowflake snowflake;

    @Test
    public void testSave() {
        Article article = new Article(1L, RandomUtil.randomString(20),
                RandomUtil.randomString(150),
                DateUtil.date(), DateUtil.date(),
                0L, 0L);
        articleRepository.save(article);
        log.info("[article] = {}", JSONUtil.toJsonStr(article));
    }

    @Test
    public void testSaveList() {
        List<Article> articleList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            articleList.add(new Article(snowflake.nextId(), RandomUtil.randomString(20),
                    RandomUtil.randomString(150),
                    DateUtil.date(), DateUtil.date(),
                    0L, 0L));
        }
        articleRepository.saveAll(articleList);

        log.info("[articles] = {}", JSONUtil.toJsonStr(articleList
                .stream()
                .map(Article::getId)
                .collect(Collectors.toList())));
    }

    @Test
    public void testUpdate() {
        articleRepository.findById(1L).ifPresent(article -> {
            article.setTitle(article.getTitle() + "更新");
            article.setUpdateTime(DateUtil.date());
            articleRepository.save(article);
            log.info("[article] = {}", JSONUtil.toJsonStr(article));
        });
    }

    @Test
    public void testDelete() {
        articleRepository.deleteById(1L);
        articleRepository.deleteAll();
    }

    @Test
    public void testThumbUp() {
        articleRepository.findById(1L).ifPresent(article -> {
            article.setThumbUp(article.getThumbUp() + 1);
            article.setVisits(article.getVisits() + 1);
            articleRepository.save(article);
            log.info("[标题] = {}, [点赞数] = {}, [访客数] = {}",
                    article.getTitle(), article.getThumbUp(), article.getVisits());
        });
    }

    @Test
    public void testThumbUp2() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(1L));
        Update update = new Update();
        update.inc("thumbUp", 1L);
        update.inc("visits", 1L);
        mongoTemplate.updateFirst(query, update, "article");

        articleRepository.findById(1L).ifPresent(article -> log.info("[标题] = {}, [点赞数] = {}, [访客数] = {}",
                article.getTitle(), article.getThumbUp(), article.getVisits()));
    }

    @Test
    public void testQuery() {
        Sort sort = Sort.by("thumbUp", "updateTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 5, sort);
        Page<Article> articlePage = articleRepository.findAll(pageRequest);
        log.info("[总页数] = {}", articlePage.getTotalPages());
        log.info("[总条数] = {}", articlePage.getTotalElements());
        log.info("[当前页数据] = {}", JSONUtil.toJsonStr(articlePage.getContent()
                .stream()
                .map(article -> "文章标题: " + article.getTitle() + " 点赞数: " + article.getThumbUp() + " 更新时间: " + article.getUpdateTime())
                .collect(Collectors.toList())));
    }

    @Test
    public void testFindByTitleLike() {
        List<Article> articleList = articleRepository.findByTitleLike("更新");
        log.info("[articles] = {}", JSONUtil.toJsonStr(articleList));
    }
}
