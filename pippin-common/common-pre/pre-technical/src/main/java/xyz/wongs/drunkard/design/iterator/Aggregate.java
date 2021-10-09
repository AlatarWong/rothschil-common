package xyz.wongs.drunkard.design.iterator;

/** 定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 18:23
 * @since 1.0.0
 */
public interface Aggregate {

    void add(Object obj);

    void remove(Object obj);

    Iterator getIterator();
}
