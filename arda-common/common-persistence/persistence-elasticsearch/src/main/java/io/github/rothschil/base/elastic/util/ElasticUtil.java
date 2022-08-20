package io.github.rothschil.base.elastic.util;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.concurrent.TimeUnit;

/**
 * ElasticSearch 工具类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/28 - 10:47
 * @since 1.0.0
 */
@Slf4j
public class ElasticUtil {


    public static final String INDEX_NAME_PREFIX = "hnqymh_hpg_";

    private ElasticUtil() {
    }

    /**
     * @param clazzName 包名+类名
     * @return Class<?>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/26 0:01
     **/
    @SuppressWarnings("unused")
    public static Class<?> getClazz(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param queryBuilder 设置查询对象
     * @param from         设置from选项，确定要开始搜索的结果索引。 默认为0。
     * @param size         设置大小选项，确定要返回的搜索匹配数。 默认为10。
     * @param timeout      超时时间
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @date 2019/10/26 0:01
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder, int from, int size, int timeout) {
        //使用默认选项创建 SearchSourceBuilder 。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置查询对象。可以使任何类型的 QueryBuilder
        sourceBuilder.query(queryBuilder);
        //设置from选项，确定要开始搜索的结果索引。 默认为0。
        sourceBuilder.from(from);
        //设置大小选项，确定要返回的搜索匹配数。 默认为10。
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeout, TimeUnit.SECONDS));
        return sourceBuilder;
    }

    /**
     * @param queryBuilder builder
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @date 2019/10/26 0:01
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder) {
        return initSearchSourceBuilder(queryBuilder, 0, 10, 60);
    }
}
