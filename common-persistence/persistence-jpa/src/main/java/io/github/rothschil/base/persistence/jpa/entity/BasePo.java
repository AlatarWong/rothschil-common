package io.github.rothschil.base.persistence.jpa.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;
import io.github.rothschil.common.po.SuperPo;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 抽象实体基类，如果主键是数据库端自动生成 请使用 {@link io.github.rothschil.common.po.BasePo} ，
 * 如果是Oracle 请使用 {@link BaseOrcDate}
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/18 10:53
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class BasePo<ID extends Serializable> extends SuperPo<ID> implements Persistable<ID> {

    private static final long serialVersionUID = 1L;

    @Override
    public abstract ID getId();

    /**
     * Sets the id of the entity.
     *
     * @param id the id to set
     */
    @Override
    public abstract void setId(final ID id);

    @Override
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BasePo<?> that = (BasePo<?>) obj;
        return null != this.getId() && this.getId().equals(that.getId());
    }


    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
