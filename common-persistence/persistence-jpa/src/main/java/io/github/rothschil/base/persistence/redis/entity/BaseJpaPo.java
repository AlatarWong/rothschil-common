package io.github.rothschil.base.persistence.redis.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 抽象实体基类，不提供基础属性
 * <p/>
 * 数据库是Oracle子类只需要在类头上加 @SequenceGenerator(name="seq", sequenceName="你的sequence名字")
 * <p/>
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/18 10:53
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@MappedSuperclass
public abstract class BaseJpaPo<ID extends Serializable> implements Persistable<ID> {

    private static final long serialVersionUID = 1L;

    @Transient
    @JSONField(serialize = false)
    private String dbType;

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
        BaseJpaPo<?> that = (BaseJpaPo<?>) obj;
        return null != this.getId() && this.getId().equals(that.getId());
    }


    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
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
