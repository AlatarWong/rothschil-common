package io.github.rothschil.base.persistence.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.page.PaginationInfo;
import io.github.rothschil.common.po.BasePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 业务基类，需要被实际使用的 Service 继承。
 * 对于 Mybatis 中的 update 操作中的 int 返回值，如果需要该返回值为 受影响的行数，需要在 数据库连接的配置信息中，增加 &useAffectedRows=true
 * <ul>
 *     <li><b>insert：</b>插入 <b>n</b> 条记录，返回影响行数 <b>n</b> 。（n>=1， <b>n</b> 为  <b>0</b>  时实际为插入失败）</li>
 *     <li><b>update：</b>更新 <b>n</b> 条记录，返回影响行数 <b>n</b> 。（n>=0）</li>
 *     <li><b>delete：</b>删除 <b>n</b> 条记录，返回影响行数 <b>n</b> 。（n>=0）</li>
 * </ul>
 *
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/12/2 13:34
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public abstract class BaseService<R extends BaseMapper<T, ID>,T extends BasePo<ID>, ID extends Serializable> {

    /**
     * 需要注入 Mapper
     */
    protected R baseMpper;

    @Autowired
    public void setBaseMpper(R baseMpper) {
        this.baseMpper = baseMpper;
    }

    /**
     * 持久化一个实例
     *
     * @param t 对象信息
     * @return int
     * @date 2017/12/2 13:24
     */
    @Transactional(rollbackFor = Exception.class)
    int insert(T t) {
        return baseMpper.insert(t);
    }

    /**
     * 持久化一个实例
     *
     * @param t 实例
     * @return int
     * @date 2017/12/2 13:24
     */
    @Transactional(rollbackFor = Exception.class)
    int insertSelective(T t) {
        return baseMpper.insertSelective(t);
    }


    /**
     * 按照实例的属性，查询结果，并且对结果进行分页
     *
     * @param pgInfo 分页信息
     * @param t      查询对象实例
     * @return PageInfo<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:28
     **/
    public PageInfo<T> selectPage(PaginationInfo pgInfo, T t) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = baseMpper.getList(t);
        return new PageInfo<>(lt);
    }


    /**
     * 按照 Condition属性，查询结果，并且对结果进行分页
     *
     * @param pgInfo    分页信息
     * @param condition 条件
     * @return PageInfo<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:28
     **/
    public PageInfo<T> selectPageByCondition(PaginationInfo pgInfo, Object condition) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = baseMpper.getListByCondition(condition);
        return new PageInfo<>(lt);
    }

    /**
     * 按照 Example 属性，查询结果，并且对结果进行分页
     *
     * @param pgInfo  分页信息
     * @param example 条件
     * @return PageInfo<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:28
     **/
    public PageInfo<T> selectByExample(PaginationInfo pgInfo, Object example) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = baseMpper.getListByExample(example);
        return new PageInfo<>(lt);
    }


    /**
     * 按照主键标识删除对象实例
     *
     * @param id 主键标识
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:31
     **/
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ID id) {
        return baseMpper.deleteByPrimaryKey(id);
    }

    /**
     * 按照主键标识获取对象实例
     *
     * @param id 主键标识
     * @return T
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:31
     **/
    public T selectByPrimaryKey(ID id) {
        return baseMpper.selectByPrimaryKey(id);
    }


    /**
     * 按照既有的实例属性更新对象，不包括 BLOB
     *
     * @param t 主键标识
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:31
     **/
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(T t) {
        return baseMpper.updateByPrimaryKeySelective(t);
    }

    /**
     * 按照既有的实例属性更新对象，包括 BLOB
     *
     * @param t 主键标识
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:31
     **/
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeyWithBlob(T t) {
        return baseMpper.updateByPrimaryKeyWithBlob(t);
    }

    /**
     * 按照主键，去更新对象
     *
     * @param t 主键标识
     * @return int
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-15:31
     **/
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(T t) {
        return baseMpper.updateByPrimaryKey(t);
    }

}