package io.github.rothschil.base.elastic.service;


import io.github.rothschil.base.elastic.bo.NgxLog;
import io.github.rothschil.base.elastic.queue.AsynchOperateElastic;
import io.github.rothschil.base.elastic.queue.handler.impl.QueueTaskHandler;
import io.github.rothschil.base.elastic.util.EsFlag;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 索引抽象管理结构，提供基本属性注入 和 基本操作
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/08/29 - 16:12
 * @since 1.0.0
 */
public abstract class AbstractElasticIndexManger {

    /**
     * 默认需要查询的字段
     */
    public static final String DEFAULT_FIELD = "uri";
    public static final String IDEN_FIELD = "_id";

    public static final String DEFAULT_FIELD_RULE = "rule";
    public static final String DEFAULT_FIELD_CLENN = "clean";
    /**
     * 通配符 匹配单个字段
     */
    public static final String SINGLE_CHARACTER = "?";

    /**
     * 通配符 匹配多字段
     */
    public static final String MULTI_CHARACTER = "*";

    protected ElasticsearchRestTemplate elasticsearchRestTemplate;

    protected RestHighLevelClient restHighLevelClient;

    protected AsynchOperateElastic asynchOperateElastic;

    protected QueueTaskHandler queueTaskHandler;

    @Autowired
    public void setAsynchOperateElastic(AsynchOperateElastic asynchOperateElastic) {
        this.asynchOperateElastic = asynchOperateElastic;
    }

    @Autowired
    public void setQueueTaskHandler(QueueTaskHandler queueTaskHandler) {
        this.queueTaskHandler = queueTaskHandler;
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Autowired
    public void setElasticsearchRestTemplate(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    /**
     * 构建匹配的 Query
     *
     * @return BoolQueryBuilder
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/7-15:18
     **/
    protected BoolQueryBuilder buildMatched() {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.mustNot(QueryBuilders.termQuery(DEFAULT_FIELD_RULE, EsFlag.DEFAULT_FLAG));
        builder.must(QueryBuilders.termQuery(DEFAULT_FIELD_CLENN, EsFlag.DEFAULT_FLAG));
        return builder;
    }

    /**
     * 构建未匹配的 Query
     *
     * @return BoolQueryBuilder
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/7-15:18
     **/
    protected BoolQueryBuilder buildNotMatched() {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery(DEFAULT_FIELD_RULE, EsFlag.DEFAULT_FLAG));
        return builder;
    }

    /**
     * 构建清理的 Query
     *
     * @return BoolQueryBuilder
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/7-15:18
     **/
    protected BoolQueryBuilder buildCleanUp() {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.mustNot(QueryBuilders.termQuery(DEFAULT_FIELD_RULE, EsFlag.DEFAULT_FLAG));
        builder.must(QueryBuilders.termQuery(DEFAULT_FIELD_CLENN, EsFlag.CLEAN_UP));
        return builder;
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
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected SearchHits search(BoolQueryBuilder builder, String... indexNames) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        Pageable pageable = PageRequest.of(1, 20);
        nativeSearchQueryBuilder.withPageable(pageable);
        return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), NgxLog.class, IndexCoordinates.of(indexNames));
    }

    /**
     * 查询匹配条件，支持同时对多个索引进行查询，只要将索引名称按照 字符数组形式组成即可
     *
     * @param builder    BoolQueryBuilder类型查询实例
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected SearchHits<NgxLog> searchPage(int page, int size, BoolQueryBuilder builder, String... indexNames) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        Pageable pageable = PageRequest.of(page, size);
        nativeSearchQueryBuilder.withPageable(pageable);
        return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), NgxLog.class, IndexCoordinates.of(indexNames));
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
     * @param isClean    默认为 True
     * @param builder    BoolQueryBuilder类型查询实例
     * @param indexNames 索引名，可以一次性查询多个
     * @return long 最终数量
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/1-9:26
     **/
    protected BulkByScrollResponse update(boolean isClean,Long ruleId, BoolQueryBuilder builder, String... indexNames) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(DEFAULT_FIELD_RULE, ruleId);
        String code;
        if(!isClean){
            code = "ctx._source.rule=params.rule;";
        } else{
            params.put(DEFAULT_FIELD_CLENN, EsFlag.CLEAN_UP);
            code = "ctx._source.rule=params.rule;ctx._source.clean=params.clean;";
        }
        ScriptType type = ScriptType.INLINE;
        //使用脚本进行更新字段值
        Script script = new Script(type, Script.DEFAULT_SCRIPT_LANG, code, params);
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
//
//    /**
//     * 逻辑 与 的条件封装
//     *
//     * @param builder builder构建
//     * @param details 条目
//     * @author <a href="https://github.com/rothschil">Sam</a>
//     * @date 2021/11/1-10:13
//     **/
//    protected void logicalAnd(BoolQueryBuilder builder, List<AtDetailsVO> details) {
//        for (AtDetailsVO detail : details) {
//            Integer type = detail.getDetailsType();
//            switch (type) {
//                case 11:
//                    // 前匹配
//                    builder.must(QueryBuilders.prefixQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 12:
//                    // 后匹配 通配符 多字符模式
//                    builder.must(QueryBuilders.wildcardQuery(DEFAULT_FIELD, MULTI_CHARACTER + detail.getDetailsValue()));
//                    break;
//                case 13:
//                    // 精确匹配
//                    builder.must(QueryBuilders.termQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 16:
//                    // 后匹配 通配 单字符符模式
//                    builder.must(QueryBuilders.wildcardQuery(DEFAULT_FIELD, SINGLE_CHARACTER + detail.getDetailsValue()));
//                    break;
//                default:
//                    // 正则 待补充
//                    builder.must(QueryBuilders.matchQuery(DEFAULT_FIELD, detail.getDetailsValue()).operator(Operator.AND));
//                    break;
//            }
//        }
//    }
//
//
//
//    /**
//     * 逻辑 或 的条件封装
//     *
//     * @param builder builder构建
//     * @param details 条目
//     * @author <a href="https://github.com/rothschil">Sam</a>
//     * @date 2021/11/1-10:13
//     **/
//    protected void LogicalOr(BoolQueryBuilder builder, List<AtDetailsVO> details) {
//        for (AtDetailsVO detail : details) {
//            Integer type = detail.getDetailsType();
//            switch (type) {
//                case 11:
//                    // 前匹配
//                    builder.should(QueryBuilders.prefixQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 12:
//                    // 后匹配 通配符 多字符模式
//                    builder.should(QueryBuilders.wildcardQuery(DEFAULT_FIELD, MULTI_CHARACTER + detail.getDetailsValue()));
//                    break;
//                case 13:
//                    // 精确匹配
//                    builder.should(QueryBuilders.termQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 16:
//                    // 后匹配 通配 单字符符模式
//                    builder.should(QueryBuilders.wildcardQuery(DEFAULT_FIELD, SINGLE_CHARACTER + detail.getDetailsValue()));
//                    break;
//                default:
//                    // 正则 待补充
//                    builder.should(QueryBuilders.matchQuery(DEFAULT_FIELD, detail.getDetailsValue()).operator(Operator.AND));
//                    break;
//            }
//        }
//    }
//
//    /**
//     * 逻辑 与 的条件封装
//     *
//     * @param builder builder构建
//     * @param details 条目
//     * @author <a href="https://github.com/rothschil">Sam</a>
//     * @date 2021/11/1-10:13
//     **/
//    protected void logicalAndBetter(BoolQueryBuilder builder, List<AtDetailsEntity> details) {
//        for (AtDetailsEntity detail : details) {
//            Integer type = detail.getDetailsType();
//            switch (type) {
//                case 11:
//                    // 前匹配
//                    builder.must(QueryBuilders.prefixQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 12:
//                    // 后匹配 通配符 多字符模式
//                    builder.must(QueryBuilders.wildcardQuery(DEFAULT_FIELD, MULTI_CHARACTER + detail.getDetailsValue()));
//                    break;
//                case 13:
//                    // 精确匹配
//                    builder.must(QueryBuilders.termQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 16:
//                    // 后匹配 通配 单字符符模式
//                    builder.must(QueryBuilders.wildcardQuery(DEFAULT_FIELD, SINGLE_CHARACTER + detail.getDetailsValue()));
//                    break;
//                default:
//                    // 正则 待补充
//                    builder.must(QueryBuilders.matchQuery(DEFAULT_FIELD, detail.getDetailsValue()).operator(Operator.AND));
//                    break;
//            }
//        }
//    }
//
//
//    /**
//     * 逻辑 或 的条件封装
//     *
//     * @param builder builder构建
//     * @param details 条目
//     * @author <a href="https://github.com/rothschil">Sam</a>
//     * @date 2021/11/1-10:13
//     **/
//    protected void LogicalOrBetter(BoolQueryBuilder builder, List<AtDetailsEntity> details) {
//        for (AtDetailsEntity detail : details) {
//            Integer type = detail.getDetailsType();
//            switch (type) {
//                case 11:
//                    // 前匹配
//                    builder.should(QueryBuilders.prefixQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 12:
//                    // 后匹配 通配符 多字符模式
//                    builder.should(QueryBuilders.wildcardQuery(DEFAULT_FIELD, MULTI_CHARACTER + detail.getDetailsValue()));
//                    break;
//                case 13:
//                    // 精确匹配
//                    builder.should(QueryBuilders.termQuery(DEFAULT_FIELD, detail.getDetailsValue()));
//                    break;
//                case 16:
//                    // 后匹配 通配 单字符符模式
//                    builder.should(QueryBuilders.wildcardQuery(DEFAULT_FIELD, SINGLE_CHARACTER + detail.getDetailsValue()));
//                    break;
//                default:
//                    // 正则 待补充
//                    builder.should(QueryBuilders.matchQuery(DEFAULT_FIELD, detail.getDetailsValue()).operator(Operator.AND));
//                    break;
//            }
//        }
//    }
}
