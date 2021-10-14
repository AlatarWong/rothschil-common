package io.github.rothschil.design.iterator;

import java.util.List;

/** 定义迭代器
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 18:29
 * @since 1.0.0
 */
public class ConcreteIterator implements Iterator {

    private List<Object> list = null;
    private int index = -1;


    public ConcreteIterator(List<Object> list){
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        if (index < list.size() - 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object first() {
        index = 0;
        Object obj = list.get(index);
        ;
        return obj;
    }


    @Override
    public Object next() {
        Object obj = null;
        if (this.hasNext()) {
            obj = list.get(++index);
        }
        return obj;
    }


}
