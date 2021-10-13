package xyz.wongs.drunkard.base.persistence.jpa.service;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.drunkard.base.persistence.jpa.entity.BasePo;
import xyz.wongs.drunkard.common.utils.Reflections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA 中封装的Service基类
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/18 11:05
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<T extends BasePo<?>, ID extends Serializable> {

    protected JpaRepository<T, ID> jpaRepository;

    public BaseService() {
    }

    /**
     * 重要
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    public abstract void setJpaRepository(JpaRepository<T, ID> jpaRepository);

    /**
     * @param t 实体信息
     * @return List
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:16
     **/
    public List<T> findByEntity(T t) {
        return jpaRepository.findAll(getExample(t));
    }

    /**
     * @param page 页
     * @param size 每页数量
     * @param t    实体信息
     * @return Page
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:16
     **/
    public Page<T> findPageByEntity(int page, int size, T t) {
        size = size == 0 ? 10 : size;
        return jpaRepository.findAll(getExample(t), PageRequest.of(page, size));
    }

    /**
     * @param t 实体信息
     * @return Example
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:16
     **/
    private Example<T> getExample(T t) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        List<String> fields = new ArrayList<>();
        Reflections.getField(t, fields);
        for (String fld : fields) {
            matcher.withMatcher(fld, ExampleMatcher.GenericPropertyMatchers.exact());
        }
        return Example.of(t, matcher);
    }

    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return T  返回id对应的实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    @Transactional(rollbackFor = Exception.class)
    public T save(T t) {
        return jpaRepository.save(t);
    }

    /**
     * 保存
     *
     * @param t 实体
     * @return T  返回id对应的实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    @Transactional(rollbackFor = Exception.class)
    public T saveAndFlush(T t) {
        t = save(t);
        jpaRepository.flush();
        return t;
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     * @author <a href="https://github.com/rothschil">Sam</a>
     * //TODO
     * @date 2019/11/8-14:02
     **/
    @Transactional(rollbackFor = Exception.class)
    public void delete(ID id) {
        jpaRepository.delete(findOne(id));
    }

    /**
     * 删除实体
     *
     * @param t 实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    @Transactional(rollbackFor = Exception.class)
    public void delete(T t) {
        jpaRepository.delete(t);
    }

    /**
     * 按照主键查询
     *
     * @param id id 主键
     * @return T  返回id对应的实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    public T findOne(ID id) {
        return jpaRepository.getById(id);
    }

    /**
     * 实体是否存在
     *
     * @param id id 主键
     * @return boolean   存在 返回true，否则false
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    public boolean exists(ID id) {
        return findOne(id) == null;
    }

    /**
     * 统计实体总数
     *
     * @return List<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/7/3 22:07
     **/
    public long count() {
        return jpaRepository.count();
    }

    /**
     * 查询所有实体
     *
     * @return List<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/7/3 22:07
     **/
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    /**
     * 按照顺序查询所有实体
     *
     * @param sort 排序实例
     * @return List<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    public List<T> findAll(Sort sort) {
        return jpaRepository.findAll(sort);
    }

    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return Page<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    public Page<T> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    /**
     * 分页
     *
     * @param page 页数
     * @param size 每页数量
     * @return Page<T>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:02
     **/
    public Page<T> findEntityNoCriteria(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return findAll(pageable);
    }

}
