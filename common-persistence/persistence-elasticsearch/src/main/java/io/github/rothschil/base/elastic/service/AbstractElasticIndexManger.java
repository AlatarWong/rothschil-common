package io.github.rothschil.base.elastic.service;


import io.github.rothschil.base.elastic.entity.BoolCondition;
import io.github.rothschil.base.elastic.entity.AtomicCondition;
import io.github.rothschil.common.constant.Constants;
import io.github.rothschil.common.po.BasePo;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.collect.Tuple;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 索引抽象管理结构，提供基本属性注入 和 基本操作
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/08/29 - 16:12
 * @since 1.0.0
 */
public abstract class AbstractElasticIndexManger {

    protected ElasticsearchRestTemplate elasticsearchRestTemplate;

    protected RestHighLevelClient restHighLevelClient;

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Autowired
    public void setElasticsearchRestTemplate(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    /**
     * 设置分片 和 副本
     * 副本作用主要为了保证数据安全
     *
     * @param request 请求
     * @author WCNGS@QQ.COM
     * @date 2019/10/17 19:27
     */
    protected void buildSetting(CreateIndexRequest request, int replicas, int shards) {
        request.settings(Settings.builder().put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas));
    }

    /**
     * 查询匹配条件的数据量，支持同时对多个索引进行查询，只要将索引名称按照 字符数组形式组成即可
     *
     * @param builder    BoolQueryBuilder类型查询实例
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected long count(BoolQueryBuilder builder, String... indexNames) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        return elasticsearchRestTemplate.count(nativeSearchQueryBuilder.build(), IndexCoordinates.of(indexNames));
    }

    /**
     * 查询匹配条件，支持同时对多个索引进行查询，只要将索引名称按照 字符数组形式组成即可
     *
     * @param builder    BoolQueryBuilder类型查询实例
     * @param clazz      Class对象
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected SearchHits search(BoolQueryBuilder builder, Class<? extends BasePo> clazz, String... indexNames) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        Pageable pageable = PageRequest.of(1, 20);
        nativeSearchQueryBuilder.withPageable(pageable);
        return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), clazz, IndexCoordinates.of(indexNames));
    }

    /**
     * 查询匹配条件，支持同时对多个索引进行查询，只要将索引名称按照 字符数组形式组成即可
     *
     * @param builder    BoolQueryBuilder类型查询实例
     * @param clazz      Class对象
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected SearchHits<? extends BasePo> searchPage(int page, int size, BoolQueryBuilder builder, Class<? extends BasePo> clazz, String... indexNames) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        Pageable pageable = PageRequest.of(page, size);
        nativeSearchQueryBuilder.withPageable(pageable);
        return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), clazz, IndexCoordinates.of(indexNames));
    }

    protected DeleteByQueryRequest builderDeleteRequest(QueryBuilder builder, String... indexNames) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexNames);
        request.setQuery(builder);
        request.setBatchSize(0X5F5E0FF);
        request.setConflicts("proceed");
        return request;
    }

    /**
     * 查询匹配条件，支持同时对多个索引进行查询，只要将索引名称按照 字符数组形式组成即可
     *
     * @param params     Map形式的 字段名 和 字段内容 组成的条件
     * @param builder    BoolQueryBuilder类型查询实例
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected BulkByScrollResponse update(Map<String, Object> params, BoolQueryBuilder builder, String... indexNames) {
        Script script = buildScriptType(params);
        UpdateByQueryRequest request = new UpdateByQueryRequest(indexNames);
        request.setQuery(builder);
        request.setScript(script);
        request.setConflicts("proceed");
        request.setRefresh(true);
        request.setTimeout(TimeValue.timeValueMinutes(3));
        try {
            return restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以 K-V键值对 方式构建条件 Script
     *
     * @param params Map形式的 字段名 和 字段内容 组成的条件
     * @return Script
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/28-13:19
     **/
    protected Script buildScriptType(Map<String, Object> params) {
        Set<String> keys = params.keySet();
        StringBuffer idOrCodeStb = new StringBuffer();
        for (String key : keys) {
            idOrCodeStb.append("ctx._source.").append(key).append("=params.").append(key).append(";");
        }
        ScriptType type = ScriptType.INLINE;
        return new Script(type, Script.DEFAULT_SCRIPT_LANG, idOrCodeStb.toString(), params);
    }

    /**
     * @param builder BoolQueryBuilder
     * @param bool    布尔类条件
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/28-14:45
     **/
    protected void setBuilders(BoolQueryBuilder builder, BoolCondition bool) {
        mustBuilders(builder, bool);
        mustNotBuilders(builder, bool);
        shouldBuilders(builder, bool);
        filterBuilders(builder, bool);
    }

    /**
     * 构建满足 必须 条件 的方法
     *
     * @param builder BoolQueryBuilder
     * @param bool    布尔类条件
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/28-14:45
     **/
    protected void mustBuilders(BoolQueryBuilder builder, BoolCondition bool) {
        List<AtomicCondition> must = bool.getMust();
        if (must.isEmpty()) {
            return;
        }
        for (AtomicCondition cds : must) {
            builder.must(getQueryBuilder(cds));
        }
    }

    /**
     * 构建满足 非必须 条件 的方法
     *
     * @param builder BoolQueryBuilder
     * @param bool    布尔类条件
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/28-14:45
     **/
    protected void mustNotBuilders(BoolQueryBuilder builder, BoolCondition bool) {
        List<AtomicCondition> mustNot = bool.getMustNot();
        if (mustNot.isEmpty()) {
            return;
        }
        for (AtomicCondition cds : mustNot) {
            builder.mustNot(getQueryBuilder(cds));
        }
    }

    /**
     * 构建满足 可选 条件 的方法
     *
     * @param builder BoolQueryBuilder
     * @param bool    布尔类条件
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/28-14:45
     **/
    protected void shouldBuilders(BoolQueryBuilder builder, BoolCondition bool) {
        List<AtomicCondition> should = bool.getShould();
        if (should.isEmpty()) {
            return;
        }
        for (AtomicCondition cds : should) {
            builder.should(getQueryBuilder(cds));
        }
    }

    /**
     * 构建满足 必须 条件 的方法，推荐使用
     *
     * @param builder BoolQueryBuilder
     * @param bool    布尔类条件
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/28-14:45
     **/
    protected void filterBuilders(BoolQueryBuilder builder, BoolCondition bool) {
        List<AtomicCondition> filter = bool.getFilter();
        if (filter.isEmpty()) {
            return;
        }
        for (AtomicCondition cds : filter) {
            builder.filter(getQueryBuilder(cds));
        }
    }

    public QueryBuilder getQueryBuilder(AtomicCondition cds) {
        QueryBuilder queryBuilder;
        Tuple tuple = cds.getTuple();
        switch (cds.getStatus()) {
            case (Constants.SUFFIX_QUERY):
                queryBuilder = QueryBuilders.wildcardQuery(cds.getField(), Constants.MULTI_CHARACTER + tuple.v1());
                break;
            case (Constants.SUFFIX_SINGLE_QUERY):
                queryBuilder = QueryBuilders.wildcardQuery(cds.getField(), Constants.SINGLE_CHARACTER + tuple.v1());
                break;
            case (Constants.RANGE_QUERY):
                queryBuilder = QueryBuilders.rangeQuery(cds.getField()).from(tuple.v1()).to(tuple.v2());
                break;
            case (Constants.PREFIX_QUERY):
                queryBuilder = QueryBuilders.prefixQuery(cds.getField(), tuple.v1().toString());
                break;
            case (Constants.REG_QUERY):
                queryBuilder = QueryBuilders.regexpQuery(cds.getField(), tuple.v1().toString());
                break;
            default:
                queryBuilder = QueryBuilders.termQuery(cds.getField(), tuple.v1().toString());
                break;
        }
        return queryBuilder;
    }
}
