package io.github.rothschil.base.elastic.bo.tuple;

/**
 * 借鉴 Python 元组设计，同时返回多个值，此处定义两个
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/21 - 17:19
 * @since 1.0.0
 */
public class ReTwoTuple<P, T> {

    /**
     * 第一个实例
     */
    public final P fp;

    /**
     * 第二个实例
     */
    public final T st;

    public ReTwoTuple(P fp, T st) {
        this.fp = fp;
        this.st = st;
    }
}
