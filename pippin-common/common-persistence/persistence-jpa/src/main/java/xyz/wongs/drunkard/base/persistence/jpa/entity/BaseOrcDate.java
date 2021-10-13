package xyz.wongs.drunkard.base.persistence.jpa.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * <p> 如果是如mysql这种自动生成主键的，请参考{@link xyz.wongs.drunkard.common.po.BasePo}
 * <p/>
 * 子类只需要在类头上加 @SequenceGenerator(name="seq", sequenceName="你的sequence名字")
 * <p/>
 * <p/>
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/8 - 13:54
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@MappedSuperclass
public abstract class BaseOrcDate<PK extends Serializable> extends BasePo<PK> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库类型
     */
    @Transient
    @JSONField(serialize = false)
    private String dbType;

    /**
     * 搜索值
     */
    @Transient
    private String searchValue;

    @Column(name = "CREATE_TIME", columnDefinition = "DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "CREATE_BY", length = 12)
    private Long createBy;

    @Column(name = "UPDATE_TIME", columnDefinition = "DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "UPDATE_BY", length = 12)
    private Long updateBy;

    @Column(name = "STATUS", length = 4)
    private String status;

    @Column(name = "REMARK", length = 12)
    private Long remark;


}
