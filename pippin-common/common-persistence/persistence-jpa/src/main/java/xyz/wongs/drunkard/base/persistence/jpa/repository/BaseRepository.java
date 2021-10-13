package xyz.wongs.drunkard.base.persistence.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import xyz.wongs.drunkard.base.persistence.jpa.entity.BasePo;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.List;

/**
 * <p>抽象DAO层基类 提供一些简便方法<br/>
 * 想要使用该接口需要在spring配置文件的jpa:repositories中添加
 * <p/>
 * <p>泛型 ： M 表示实体类型；ID表示主键类型
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/7/8 - 13:59
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface BaseRepository<T extends BasePo, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 根据主键删除
     *
     * @param ids 主键数组
     */
    void delete(ID[] ids);

    /**
     * 根据SQL，查询结果，获取结果列表
     *
     * @param sql 原生SQL
     * @return List
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    List<T> listBySql(String sql);

    /**
     * 根据HQL，查询结果，获取结果列表
     *
     * @param hql HQL语句
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    List<T> listByHql(String hql);

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
     * @param hql  HQL语句
     * @param args 参数
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
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
     * 按照分页方式查询
     *
     * @param t        非空
     * @param pageable 非空
     * @param list     多种查询条件,可以自定义实现，拓展为动态查询，可以为空，为空时候，自动从实体的属性中获取
     * @return org.springframework.data.domain.Page<T>
     * @date 20/12/22 16:25
     */
    Page<T> findByCriteriaQuery(T t, Pageable pageable, List<Predicate> list);

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

}
