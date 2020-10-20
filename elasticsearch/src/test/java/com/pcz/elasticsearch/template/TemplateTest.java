package com.pcz.elasticsearch.template;

import com.pcz.elasticsearch.SpringBootElasticsearchApplicationTest;
import com.pcz.elasticsearch.model.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

public class TemplateTest extends SpringBootElasticsearchApplicationTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Person.class);
        elasticsearchTemplate.putMapping(Person.class);
    }

    @Test
    public void testDeleteIndex() {
        elasticsearchTemplate.deleteIndex(Person.class);
    }
}
