package io.github.rothschil.base.elastic.entity;

import org.elasticsearch.common.collect.Tuple;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/28 - 13:37
 * @since 1.0.0
 */
public class Conditions {

    /**
     * 条件
     */
    private int status;

    /**
     * 字段
     */
    private String field;

    /**
     * 内容，两个值
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

//    public String getValue() {
//        switch (status){
//            case(Constants.SUFFIX_QUERY):
//                value= Constants.MULTI_CHARACTER +value;
//                break;
//            case(Constants.SUFFIX_SINGLE_QUERY):
//                value= Constants.SINGLE_CHARACTER +value;
//                break;
//            default:
//                break;
//        }
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }

    public Tuple getTuple() {
        return tuple;
    }

    public void setTuple(Tuple tuple) {
        this.tuple = tuple;
    }
}
