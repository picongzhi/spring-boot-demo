package com.pcz.elasticsearch.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.pcz.elasticsearch.config.ElasticsearchProperties;
import com.pcz.elasticsearch.exception.ElasticsearchException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author picongzhi
 */
@Slf4j
public abstract class BaseElasticsearchService {
    @Resource
    protected RestHighLevelClient client;

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    protected static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    /**
     * 创建索引
     *
     * @param index 索引
     */
    protected void createIndexRequest(String index) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            request.settings(Settings.builder()
                    .put("index.number_of_shards", elasticsearchProperties.getIndex().getNumberOfShards())
                    .put("index.number_of_replicas", elasticsearchProperties.getIndex().getNumberOfReplicas()));
            CreateIndexResponse response = client.indices().create(request, COMMON_OPTIONS);

            log.info("whether all of the nodes has acknowledged the request: {}",
                    response.isAcknowledged());
            log.info("indicates whether the requisite number of shard copies were started for each shard in the index before timing out: {}",
                    response.isShardsAcknowledged());
        } catch (IOException e) {
            throw new ElasticsearchException("创建索引 [" + index + "] 失败");
        }
    }

    /**
     * 删除索引
     *
     * @param index 索引
     */
    protected void deleteIndexRequest(String index) {
        DeleteIndexRequest request = buildDeleteIndexRequest(index);
        try {
            client.indices().delete(request, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException("删除索引 [" + index + "] 失败");
        }
    }

    /**
     * 构造DeleteIndexRequest
     *
     * @param index 索引
     * @return DeleteIndexRequest
     */
    private static DeleteIndexRequest buildDeleteIndexRequest(String index) {
        return new DeleteIndexRequest(index);
    }

    /**
     * 构造IndexRequest
     *
     * @param index  索引
     * @param id     id
     * @param object 数据
     * @return IndexRequest
     */
    protected static IndexRequest buildIndexRequest(String index, String id, Object object) {
        return new IndexRequest(index).id(id).source(BeanUtil.beanToMap(object), XContentType.JSON);
    }

    /**
     * 更新指定id的文档
     *
     * @param index  索引
     * @param id     id
     * @param object 数据
     */
    protected void updateRequest(String index, String id, Object object) {
        try {
            UpdateRequest request = new UpdateRequest(index, id)
                    .doc(BeanUtil.beanToMap(object), XContentType.JSON);
            client.update(request, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException("更新索引 [" + index + "] 数据 [" + object + "] 失败");
        }
    }

    /**
     * 删除指定id的文档
     *
     * @param index 索引
     * @param id    id
     */
    protected void deleteRequest(String index, String id) {
        try {
            DeleteRequest request = new DeleteRequest(index, id);
            client.delete(request, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException("删除索引 [" + index + "] 数据id [" + id + "] 失败");

        }
    }

    /**
     * 查询所有
     *
     * @param index 索引
     * @return SearchResponse
     */
    protected SearchResponse search(String index) {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(request, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
