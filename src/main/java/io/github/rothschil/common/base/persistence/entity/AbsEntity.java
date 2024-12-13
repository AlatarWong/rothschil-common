package io.github.rothschil.common.base.persistence.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbsEntity<ID extends Serializable> extends AbstractEntity<ID> implements Persistable<ID> {

	private static final long serialVersionUID = 1L;

	@Override
	public abstract ID getId();

    /**
     * Sets the id of the entity.
     * @param id the id to set
     */
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

        AbsEntity<?> that = (AbsEntity<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
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
