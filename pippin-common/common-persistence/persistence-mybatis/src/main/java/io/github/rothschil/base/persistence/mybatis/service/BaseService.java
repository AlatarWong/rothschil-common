package io.github.rothschil.base.persistence.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.page.PaginationInfo;
import io.github.rothschil.common.po.BasePo;

import java.io.Serializable;
import java.util.List;

/**
 * @author WCNGS@QQ.COM
 * @date 2017/12/2 13:34
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public abstract class BaseService<T extends BasePo, ID extends Serializable> {

    /**
     * 待补充
     */
    protected abstract BaseMapper<T, ID> getMapper();

    /**
     * 持久化一个对象
     *
     * @param t 对象信息
     * @return int
     * @date 2017/12/2 13:24
     */
    @Transactional(rollbackFor = Exception.class)
    int insert(T t) {
        return getMapper().insert(t);
    }

    /**
     * 持久化一个对象
     *
     * @param t 对象信息
     * @return int
     * @date 2017/12/2 13:24
     */
    @Transactional(rollbackFor = Exception.class)
    int insertSelective(T t) {
        return getMapper().insertSelective(t);
    }


    public PageInfo<T> selectPage(PaginationInfo pgInfo, T t) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = getMapper().getList(t);
        return new PageInfo<>(lt);
    }

    public PageInfo<T> selectPageByCondition(PaginationInfo pgInfo, Object condition) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = getMapper().getListByCondition(condition);
        return new PageInfo<>(lt);
    }

    public PageInfo<T> selectByExample(PaginationInfo pgInfo, Object example) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = getMapper().getListByExample(example);
        return new PageInfo<>(lt);
    }


    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ID id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    public T selectByPrimaryKey(ID id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeyWithBlob(T t) {
        return getMapper().updateByPrimaryKeyWithBlob(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(T t) {
        return getMapper().updateByPrimaryKey(t);
    }

}