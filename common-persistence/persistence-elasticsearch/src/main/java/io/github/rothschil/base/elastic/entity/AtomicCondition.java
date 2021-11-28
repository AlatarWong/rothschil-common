package io.github.rothschil.base.elastic.entity;

import org.elasticsearch.common.collect.Tuple;

/**
 * 构建布尔类查询基础的基本单元，当前匹配可支持的类型有：前缀、后缀、精确、正则、区间五大类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/28 - 13:37
 * @since 1.0.0
 */
public class AtomicCondition {

    /**
     * 匹配的基本类型，
     */
    private int status;

    /**
     * 字段
     */
    private String field;

    /**
     * 元组，可随意存储两个值
     */
    private Tuple tuple;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Tuple getTuple() {
        return tuple;
    }

    public void setTuple(Tuple tuple) {
        this.tuple = tuple;
    }
}
