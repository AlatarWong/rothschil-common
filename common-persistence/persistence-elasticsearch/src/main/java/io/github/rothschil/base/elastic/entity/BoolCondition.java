package io.github.rothschil.base.elastic.entity;

import java.util.List;

/**
 * 条件封装
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/28 - 13:59
 * @since 1.0.0
 */
public class BoolCondition {

    /**
     * 必须符合的条件
     */
    private List<AtomicCondition> must;

    /**
     * 不需要符合
     */
    private List<AtomicCondition> mustNot;

    /**
     * 非必要 可选
     */
    private List<AtomicCondition> should;

    /**
     * 条件，效率高，推荐使用
     */
    private List<AtomicCondition> filter;


    public List<AtomicCondition> getMust() {
        return must;
    }

    public void setMust(List<AtomicCondition> must) {
        this.must = must;
    }

    public List<AtomicCondition> getMustNot() {
        return mustNot;
    }

    public void setMustNot(List<AtomicCondition> mustNot) {
        this.mustNot = mustNot;
    }

    public List<AtomicCondition> getShould() {
        return should;
    }

    public void setShould(List<AtomicCondition> should) {
        this.should = should;
    }

    public List<AtomicCondition> getFilter() {
        return filter;
    }

    public void setFilter(List<AtomicCondition> filter) {
        this.filter = filter;
    }
}

