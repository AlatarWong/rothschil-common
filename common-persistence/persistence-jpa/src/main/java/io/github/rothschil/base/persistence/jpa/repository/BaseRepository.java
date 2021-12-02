package io.github.rothschil.base.persistence.jpa.repository;

import io.github.rothschil.base.persistence.jpa.entity.BaseJpaPo;
import io.github.rothschil.base.persistence.jpa.repository.factory.BaseRepositoryFactoryBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 抽象DAO层基类 提供一些简便和自定义的方法
 * 想要使用该接口需要在 <b>SpringBoot 启动类中</b> 配置我们自定义的 {@link BaseRepositoryFactoryBean} :
 * <hr/>
 * <b>@{@link EnableJpaRepositories}(repositoryFactoryBeanClass = {@link BaseRepositoryFactoryBean})</b>
 * <hr/>
 * <p/>
 * <p>泛型 ： T 表示实体类型；ID表示主键类型
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/7/8 - 13:59
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface BaseRepository<T extends BaseJpaPo<ID>, ID extends Serializable> extends JpaRepository<T, ID> {


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
    boolean support(String modelType);

    /**
     * 根据主键删除
     *
     * @param ids 主键数组
     */
    void delete(ID[] ids);

    /**
     * 根据SQL，查询结果，获取结果列表，返回的是按照实例的属性数组 最终合并成一个列表返回。
     *
     * @param sql 原生SQL
     * @return List
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    List<?> simpleListBySql(String sql);

    /**
     * 根据HQL，查询结果，获取结果列表
     *
     * @param hql HQL语句
     * @return List<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    List<T> simpleListByHql(String hql);

    /**
     * 根据SQL语句获取目标
     *
     * @param sql 原生SQL
     * @return Object
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    Object getTarget(String sql);

    /**
     * 根据SQL语句获取目标
     *
     * @param hql 将 HQL 转换为 Query，供 应用查询使用
     * @return Query
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    Query getQueryByHql(String hql);

    /**
     * 根据SQL语句获取目标
     *
     * @param hql 将 HQL 转换为 TypedQuery，供 应用查询使用
     * @param clazz 类对象
     * @return TypedQuery
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    TypedQuery getQueryByHql(String hql, Class<?> clazz);

    /**
     * 按照SQL执行修改命令
     *
     * @param sql  原生SQL
     * @param args 参数
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    void updateBySql(String sql, Object... args);

    /**
     * 按照HQL执行修改命令
     *
     * @param hql HQL语句
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    void updateBySimpleHql(String hql);

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
    void updateByHql(String hql, Object... args);

    /**
     * 根据实体信息查询,利用实体结合 Specification 默认设置进行分页
     *
     * @param t        实体类
     * @param pageable 分页信息
     * @return Page<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:35
     **/
    Page<T> find(T t, Pageable pageable);

    /**
     * 根据实体信息查询,利用Specification 默认设置进行分页
     *
     * @param spec     条件
     * @param pageable 分页信息
     * @return Page<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:35
     **/
    Page<T> find(Specification<T> spec, Pageable pageable);


    /**
     * 以SQL方式，执行批量插入
     *
     * @param sql 原生SQL语句
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    int batchInsert(String sql);

    /**
     * 以list方式，执行批量插入
     *
     * @param list 实体集合
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    int batchInsert(List<T> list);

    /**
     * 批量处理对象集合 ，并进行提交
     *
     * @param list 集合
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    Iterable<T> batchInsertList(List<T> list);

    /**
     * 批量处理对象集合 ，并进行更新
     *
     * @param iter 迭代器
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    Iterable<T> batchUpdate(Iterable<T> iter);

    /**
     * 批量处理对象集合 ，并进行更新
     *
     * @param list 集合
     * @return Iterable<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/12/1-15:27
     **/
    Iterable<T> batchUpdate(List<T> list);

}
