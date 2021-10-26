package io.github.rothschil.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统列表
 * </p>
 *
 * @author Bean
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("at_system")
public class AtSystem extends Model<AtSystem> {

    private static final long serialVersionUID = 1L;

    /**
     * 接入系统标识
     */
    @TableId(value = "system_id", type = IdType.AUTO)
    private Long id;
    /**
     * 系统名称
     */
    @TableField(value = "system_name")
    private String systemName;
    /**
     * 系统编码
     */
    @TableField(value = "sys_code")
    private String sysCode;
    /**
     * 系统别名
     */
    @TableField(value = "sys_alias")
    private String sysAlias;
    /**
     * 系统应用地址
     */
    @TableField(value = "app_ip")
    private String appIp;
    /**
     * 甲方管理员
     */
    @TableField(value = "contact_id")
    private Integer contactId;
    /**
     * 专业领域标识
     */
    @TableField(value = "domain_id")
    private Integer domainId;
    /**
     * 厂商管理员，同样以 联系人表为外键
     */
    @TableField(value = "contact_party")
    private Integer contactParty;
    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;




    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSysAlias() {
        return sysAlias;
    }

    public void setSysAlias(String sysAlias) {
        this.sysAlias = sysAlias;
    }

    public String getAppIp() {
        return appIp;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public Integer getContactParty() {
        return contactParty;
    }

    public void setContactParty(Integer contactParty) {
        this.contactParty = contactParty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
