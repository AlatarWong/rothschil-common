package io.github.rothschil.base.persistence.jpa.repository.impl;

import io.github.rothschil.base.persistence.jpa.entity.BaseJpaPo;
import io.github.rothschil.base.persistence.jpa.repository.BaseRepository;
import io.github.rothschil.base.persistence.jpa.util.JpaMethodUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 继承 {@link SimpleJpaRepository}  和 实现 {@link JpaRepository}, 所以具备二者各自的特性，如：
 * <ul>
 *  <li>即对 <b>CrudRepository</b> 默认实现，默认中带 {@link Transactional}，并且 readOnly = true</li>
 * <li>提供 {@link EntityManager} 可以更自由的实现个性化业务，方便了拓展</li>
 * </ul>
 * <p>
 *  通过继承 {@link SimpleJpaRepository}，拿到它的构造函数，
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/8 - 14:41
 * @since 1.0.0
 */
public class BaseRepositoryImpl<T extends BaseJpaPo<ID>, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    private final Class<T> domainClass;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.domainClass = domainClass;
        this.entityManager = em;
    }


    /**
     * 判断该 Repository 是否为 modelType 类型，实际使用在单元测试领域，根据 {@link Entity} 实例的 class 名称，获取对应的 Repository。
     * <ul>
     *     <li>通过依赖注入 <b>private {@link List}<{@link BaseRepository}> repositories</b> 获得一个 Repository 列表</li>
     *     <li>循环上述列表，利用 support(modelType) 作为判断条件，获取 Repository</li>
     *     <li>根据 Repository 做出实际操作</li>
     *
     * </ul>
     *
     * @param modelType 类型
     * @return boolean
     */
    @Override
    public boolean support(String modelType) {
        return domainClass.getName().equals(modelType);
    }

    /**
     * 按照主键的数组，批量删除
     *
     * @param ids 主键数组
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public void delete(ID[] ids) {

    }

    /**
     * 根据SQL语句获取目标
     *
     * @param sql 原生SQL语句
     * @return Object
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public Object getTarget(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getSingleResult();
    }

    /**
     * 根据SQL语句获取目标
     *
     * @param hql 将 HQL 转换为 Query，供 应用查询使用
     * @return Query
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public Query getQueryByHql(String hql) {
        return entityManager.createQuery(hql);
    }

    /**
     * 根据SQL语句获取目标
     *
     * @param hql 将 HQL 转换为 TypedQuery，供 应用查询使用
     * @param clazz 类对象
     * @return TypedQuery
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public TypedQuery getQueryByHql(String hql,Class<?> clazz) {
        return entityManager.createQuery(hql,clazz);
    }

    /**
     * 按照SQL执行修改命令
     *
     * @param sql  原生SQL语句
     * @param args 参数
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public void updateBySql(String sql, Object... args) {
        Query query = entityManager.createNativeQuery(sql);
        int i = 0;
        for (Object arg : args) {
            query.setParameter(++i, arg);
        }
        query.executeUpdate();
    }

    /**
     * 按照HQL执行修改命令
     *
     * @param hql HQL语句
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public void updateBySimpleHql(String hql) {
        entityManager.createQuery(hql).executeUpdate();
    }

    /**
     * 按照 HQL 执行修改命令，根据
     * <p>字段名、字段值、字段类型</p>
     * 不推荐使用，当 参数为日期 等复合类型 的场景，就不支持
     *
     * @param hql  HQL语句
     * @param args 参数
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Deprecated
    @Override
    public void updateByHql(String hql, Object... args) {
        Query query = getQueryByHql(hql);
        int i = 0;
        for (Object arg : args) {
            query.setParameter(++i, arg);
        }
        query.executeUpdate();
    }


    /**
     * 根据SQL，查询结果，获取结果列表，返回的是按照实例的属性数组 最终合并成一个列表返回。
     *
     * @param sql 原生SQL
     * @return List
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public List<?> simpleListBySql(String sql) {
        return entityManager.createNativeQuery(sql).getResultList();
    }

    /**
     * 根据HQL，查询结果，获取结果列表
     *
     * @param hql HQL语句
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public List<T> simpleListByHql(String hql) {
        return getQueryByHql(hql).getResultList();
    }

    /**
     * 以SQL方式，执行批量插入
     *
     * @param sql 原生SQL语句
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public int batchInsert(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }

    /**
     * 以list方式，执行批量插入
     *
     * @param list 列表集合
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public int batchInsert(List<T> list) {
        int i = 0;
        for (T t : list) {
            i++;
            entityManager.persist(t);
            if (i % 1000 == 0) {
                flush(entityManager);
            }
        }
        return 0;
    }

    /**
     * 批量处理对象集合 ，并进行提交
     *
     * @param list 集合
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    @Override
    public Iterable<T> batchInsertList(List<T> list) {
        Iterable<T> iterable = buildIterable(list);
        return batchInsert(iterable);
    }


    /**
     * 利用 List 构建一个迭代器
     *
     * @param list 集合
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    protected Iterable<T> buildIterable(List<T> list) {
        return () -> new Iterator<T>() {
            final ListIterator<T> listIter = list.listIterator(list.size());

            @Override
            public boolean hasNext() {
                return listIter.hasPrevious();
            }

            @Override
            public T next() {
                return listIter.previous();
            }

            @Override
            public void remove() {
                listIter.remove();
            }
        };
    }

    /**
     * 批量处理对象集合 ，并进行提交
     *
     * @param iter 迭代器
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    public Iterable<T> batchInsert(Iterable<T> iter) {
        Iterator<T> iterator = iter.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            entityManager.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                flush(entityManager);
            }
        }
        if (index % BATCH_SIZE != 0) {
            flush(entityManager);
        }
        return iter;
    }

    private final static int BATCH_SIZE = 100;


    /**
     * 批量处理对象集合 ，并进行更新
     *
     * @param list 集合
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    @Override
    public Iterable<T> batchUpdate(List<T> list) {
        Iterable<T> iterable = buildIterable(list);
        return batchUpdate(iterable);
    }

    /**
     * 批量处理对象集合 ，并进行更新
     *
     * @param iter 迭代器
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    @Override
    public Iterable<T> batchUpdate(Iterable<T> iter) {
        Iterator<T> iterator = iter.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            entityManager.merge(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                flush(entityManager);
            }
        }
        if (index % BATCH_SIZE != 0) {
            flush(entityManager);
        }
        return iter;
    }

    private void flush(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * 利用Specification 默认设置进行分页
     *
     * @param spec     条件
     * @param pageable 分页信息
     * @return Page<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:35
     **/
    @Override
    public Page<T> find(Specification<T> spec, Pageable pageable) {
        return super.findAll(spec, pageable);
    }

    /**
     * 利用实体结合 Specification 默认设置进行分页
     *
     * @param t        实体类
     * @param pageable 分页信息
     * @return Page<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:35
     **/
    @Override
    public Page<T> find(T t, Pageable pageable) {
        Specification spec = JpaMethodUtil.getSpecification(t);
        return find(spec, pageable);
    }


    /**
     * 获取数量
     *
     * @param query 查询CriteriaQuery
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:37
     **/
    private int getTotal(CriteriaQuery<T> query) {
        List<?> totals = entityManager.createQuery(query).getResultList();
        if (totals.isEmpty()) {
            return 0;
        }
        return totals.size();
    }


}
