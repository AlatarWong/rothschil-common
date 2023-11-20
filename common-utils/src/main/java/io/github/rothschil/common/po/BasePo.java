package io.github.rothschil.common.po;

import java.io.Serializable;

/**
 * Root 级别的抽象对象，只单纯定义ID主键标识，考虑后续有的主键可能为字符串等多种格式，故用泛型，
 * 所有 <b>业务类实体对象</b> 不建议直接继承该 {@link BasePo} 类，建议 <b>业务类实体对象</b> 直接继承 {@link BasePo}
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/12/2 13:32
 * @since 1.0.0
 */
public abstract class BasePo<ID extends Serializable> implements Serializable {

    /**
     * 获取主键
     *
     * @return ID 主键
     * @date 2017/12/2 13:23
     */
    public abstract ID getId();

    /**
     * 设置主键
     *
     * @param id 主键
     * @date 2017/12/2 13:22
     */
    public abstract void setId(ID id);
}