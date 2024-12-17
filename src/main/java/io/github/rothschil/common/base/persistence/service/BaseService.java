package io.github.rothschil.common.base.persistence.service;


import io.github.rothschil.common.base.persistence.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 *
 * Service基类
 * @author WCNGS
 * @date 2017年4月24日
 *
 */
public abstract class BaseService<R extends JpaRepository<T,ID>, T extends AbstractEntity<?>, ID extends Serializable> {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JpaRepository<T, ID> jpaRepository;


    public BaseService() {
    }



    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    public T save(T t) {
        return jpaRepository.save(t);
    }

    public T saveAndFlush(T t) {
        t = save(t);
        jpaRepository.flush();
        return t;
    }


    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    public void delete(ID id) {
        jpaRepository.deleteById(id);
    }

    /**
     * 删除实体
     *
     * @param t 实体
     */
    public void delete(T t) {
        jpaRepository.delete(t);
    }


    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public T findById(ID id) {
        Optional<T> optional= jpaRepository.findById(id);
        return optional.orElse(null);
    }


    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    @Deprecated
    public T findOne(ID id) {
        return jpaRepository.getOne(id);
    }


    /**
     * 实体是否存在
     * @author      WCNGS@QQ.COM
     * @param id                id 主键
     * @return      boolean   存在 返回true，否则false
     * @date        2018/7/3 22:08
     */
    public boolean exists(ID id) {
        return findOne(id) == null;
    }


    /**
     * 统计实体总数
     * @author      WCNGS@QQ.COM
     * @return      long
     * @date        2018/7/3 22:07
     */
    public long count() {
        return jpaRepository.count();
    }


    /**
     * 查询所有实体
     * @author      WCNGS@QQ.COM
     * @return      java.util.List<T>
     * @date        2018/7/3 22:07
     */
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    /**
     * 按照顺序查询所有实体
     * @author      WCNGS@QQ.COM
     * @param sort  排序
     * @return      java.util.List<T>
     * @date        2018/7/3 22:06
     */
    public List<T> findAll(Sort sort) {
        return jpaRepository.findAll(sort);
    }


    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return Page<T>
     * @date        2018/7/3 22:06
     */
    public Page<T> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    public Page<T> findEntityNoCriteria(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return findAll(pageable);
    }


    public Page<T> findCriteria(Integer page, Integer size,final AbstractEntity<?> ae) {
        Pageable pageable = PageRequest.of(page, size);

//        Sort sort = new Sort(Sort.Direction.DESC, "id");

        return findAll(pageable);
    }

//    /***
//     *
//     * @Title: findEntityCriteria
//     * @Description: 根据对象 ，的查询分页
//     * @param page zero-based page index.
//     * @param size the size of the page to be returned.
//     * @param t 实体对象
//     * @return  Page<T>
//     */
//    public abstract Page<T> findEntityCriteria(Integer page, Integer size, T t);
//    {
//
//    	Pageable pageable = new PageRequest(page, size);
//		Page<T> lePage = null;
//		try {
//			Example<T> example = Example.of(t);
//			lePage = jpaRepository.findAll(example, pageable);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lePage;
//    }


}
