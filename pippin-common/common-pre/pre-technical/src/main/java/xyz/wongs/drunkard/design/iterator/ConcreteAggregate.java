package xyz.wongs.drunkard.design.iterator;

import java.util.ArrayList;
import java.util.List;

/** 实现抽象聚合类，返回一个具体迭代器的实例
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 18:26
 * @since 1.0.0
 */
public class ConcreteAggregate implements Aggregate{

    private List<Object> list = new ArrayList<>();

    @Override
    public void add(Object obj) {
        list.add(obj);
    }

    @Override
    public void remove(Object obj) {
        list.remove(obj);
    }

    @Override
    public Iterator getIterator() {
        return (new ConcreteIterator(list));
    }
}
