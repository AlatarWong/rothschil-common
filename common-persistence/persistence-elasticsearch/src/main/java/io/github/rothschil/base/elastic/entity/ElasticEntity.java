package io.github.rothschil.base.elastic.entity;

import java.util.Map;

/**
 * 数据存储对象，封装类的实体对象
 *
 * @author <a href="https://github.com/rothschil">SamAbram</a>
 * @date 2019/11/21 9:10
 * @since 1.0.0
 */
public class ElasticEntity<T> {

    /**
     * 主键标识，用户ES持久化
     */
    private String id;

    /**
     * JSON对象，实际存储数据
     */
    private Map data;

    public ElasticEntity() {
    }

    public ElasticEntity(String id, Map data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
