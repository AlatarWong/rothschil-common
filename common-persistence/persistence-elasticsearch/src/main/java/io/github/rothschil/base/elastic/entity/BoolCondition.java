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
    private List<Conditions> mustConditon;

    /**
     * 不需要符合
     */
    private List<Conditions> mustNotConditon;

    /**
     * 非必要 可选择行符合
     */
    private List<Conditions> shouldConditon;

    /**
     * 条件，效率高，推荐使用
     */
    private List<Conditions> filterConditon;


    public List<Conditions> getMustConditon() {
        return mustConditon;
    }

    public void setMustConditon(List<Conditions> mustConditon) {
        this.mustConditon = mustConditon;
    }

    public List<Conditions> getMustNotConditon() {
        return mustNotConditon;
    }

    public void setMustNotConditon(List<Conditions> mustNotConditon) {
        this.mustNotConditon = mustNotConditon;
    }

    public List<Conditions> getShouldConditon() {
        return shouldConditon;
    }

    public void setShouldConditon(List<Conditions> shouldConditon) {
        this.shouldConditon = shouldConditon;
    }

    public List<Conditions> getFilterConditon() {
        return filterConditon;
    }

    public void setFilterConditon(List<Conditions> filterConditon) {
        this.filterConditon = filterConditon;
    }
}

