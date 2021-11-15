package io.github.rothschil.base.persistence.redis.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import io.github.rothschil.base.persistence.redis.entity.BaseJpaPo;
import io.github.rothschil.base.persistence.redis.repository.BaseRepository;
import io.github.rothschil.base.persistence.redis.util.JpaMethodUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * 继承 {@link SimpleJpaRepository}  和 实现 {@link JpaRepository}, 所以具备二者各自的特性，如：
 * <ul>
 *  <li>即对 <b>CrudRepository</b> 默认实现，默认中带 {@link Transactional}，并且 readOnly = true</li>
 * <li>提供 {@link EntityManager} 可以更自由的实现个性化业务，方便了拓展</li>
 * </ul>
 *
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
     *
     * @param modelType 类型
     * @return  boolean
     */
    @Override
    public boolean support(String modelType){
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
     * @param hql  HQL语句
     * @param args 参数
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    @Override
    public void updateByHql(String hql, Object... args) {
        Query query = entityManager.createQuery(hql);
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
    public List<?> listBySql(String sql) {
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
    public List<T> listByHql(String hql) {
        return entityManager.createQuery(hql).getResultList();
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
                entityManager.flush();
                entityManager.clear();
            }
        }
        return 0;
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
     * 根据实体信息查询
     *
     * @param t        非空
     * @param pageable 非空
     * @param list     多种查询条件,可以自定义实现，拓展为动态查询，可以为空，为空时候，自动从实体的属性中获取
     * @return org.springframework.data.domain.Page<T>
     * @date 20/12/22 16:25
     */
    @Override
    public Page<T> findByCriteriaQuery(T t, Pageable pageable, List<Predicate> list) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        // Root 定义查询的From子句中能出现的类型
        Root<T> root = query.from(t.getClass());

        // 多种查询条件,可以自定义实现，拓展为动态查询，可以为空，为空时候，自动从实体的属性中获取
        if (list.isEmpty()) {
            list = JpaMethodUtil.getFieldValue(t, root, cb);
        }

        List<Expression<?>> grouping = new ArrayList<Expression<?>>();
        grouping.add(root.get("id"));
        grouping.add(root.get("flag"));
        grouping.add(root.get("localCode"));
        grouping.add(root.get("localName"));
        grouping.add(root.get("lv"));
        grouping.add(root.get("supLocalCode"));
        grouping.add(root.get("url"));
        query.multiselect(
                root.get("id"),
                root.get("flag"),
                root.get("localCode"),
                root.get("localName"),
                root.get("lv"),
                root.get("supLocalCode"),
                root.get("url"),
                cb.sum(root.get("id")));


        query.where(list.toArray(new Predicate[list.size()]));
        query.groupBy(grouping);

        TypedQuery<T> typedQuery = entityManager.createQuery(query);
//        typedQuery.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
//        typedQuery.setMaxResults(pageable.getPageSize());


        long total = getTotal(query);
        List<T> content = total > typedQuery.getFirstResult() ? typedQuery.getResultList() : Collections.<T>emptyList();
        return new PageImpl<T>(content, pageable, total);
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
