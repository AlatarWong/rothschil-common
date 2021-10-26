package io.github.rothschil.base.persistence.mybatis.mapper;

import io.github.rothschil.common.po.BasePo;

import java.io.Serializable;
import java.util.List;

/**
 * 通用 Mapper
 *
 * @author WCNGS@QQ.COM
 * @date 2019/11/21 17:40
 * @since 1.0.0
 */
public interface BaseMapper<T extends BasePo<ID>, ID extends Serializable> {

    /**
     * 持久化一个对象
     *
     * @param t 实例
     * @return int
     * @date 2017/12/2 13:24
     */
    int insert(T t);

    /**
     * 按条件将数据对象持久化
     *
     * @param t 实例
     * @return int
     * @date 2017/12/2 13:24
     */
    int insertSelective(T t);

    /**
     * 获取一个对象List集合
     *
     * @param t 实体对象
     * @return List
     * @author WCNGS@QQ.COM
     * @date 2019/7/23 19:27
     */
    List<T> getList(T t);

    /**
     * 按条件查询对象反馈一个List集合
     *
     * @param condition 具体条件
     * @return List
     * @date 2017/12/2 13:24
     */
    List<T> getListByCondition(Object condition);

    /**
     * 按条件查询对象反馈一个List集合
     *
     * @param example Example对象
     * @return List
     * @date 2017/12/2 13:24
     */
    List<T> getListByExample(Object example);

    /**
     * 根据主键删除一个对象
     *
     * @param id 主键信息
     * @return int
     * @date 2017/12/2 13:24
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 按照主键查询一个对象
     *
     * @param id 主键
     * @return t 实体对象
     * @date 2017/12/2 13:24
     */
    T selectByPrimaryKey(ID id);

    /**
     * 按照主键选择性更新一个对象
     *
     * @param t 对象信息
     * @return int
     * @date 2017/12/2 13:24
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 按照主键和属性值更新一个对象
     *
     * @param t 对象信息
     * @return int
     * @date 2017/12/2 13:24
     */
    int updateByPrimaryKeyWithBlob(T t);

    /**
     * 按照主键更新一个对象
     *
     * @param t 对象信息
     * @return int
     * @date 2017/12/2 13:24
     */
    int updateByPrimaryKey(T t);

}
