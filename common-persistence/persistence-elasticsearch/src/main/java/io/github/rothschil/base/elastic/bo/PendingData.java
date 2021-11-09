package io.github.rothschil.base.elastic.bo;


import java.util.List;

/**
 * 所有需要重新发送的数据，包装类，说明索引名字和 数据列表
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/1 - 11:07
 * @since 1.0.0
 */
public class PendingData {

    public PendingData() {
    }

    public PendingData(String indexName, List<NgxLog> lists) {
        this.indexName = indexName;
        this.lists = lists;
    }

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 日志实例的集合
     */
    private List<NgxLog> lists;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<NgxLog> getLists() {
        return lists;
    }

    public void setLists(List<NgxLog> lists) {
        this.lists = lists;
    }
}
