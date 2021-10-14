package io.github.rothschil.common.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.rothschil.base.config.Global;
import io.github.rothschil.common.utils.StringUtils;
import io.github.rothschil.common.constant.Constants;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>业务类实体对象</b> 可以直接继承本类，提供基本元素属性供 Entity 实体使用。
 *
 * @author WCNGS@QQ.COM
 * @date 2017/12/2 13:32
 * @since 1.0.0
 */
public abstract class BasePo<ID extends Serializable> extends SuperPo<ID> {

    /**
     * 数据库类型
     */
    @SuppressWarnings("unused")
    @JSONField(serialize = false)
    private String dbType;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 请求参数
     */
    private Map<String, Object> params;

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/12-14:05
     * @param
     * @return String
     **/
    @SuppressWarnings("unused")
    public String getDbType() {
        String dbType = Global.getConfig(Constants.DB_TYPE);
        if(StringUtils.isBlank(dbType)){
            dbType=Constants.DEFAULT_DATABASE;
        }
        return dbType;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>(6);
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @SuppressWarnings("unused")
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @SuppressWarnings("unused")
    public String getSearchValue() {
        return searchValue;
    }

    @SuppressWarnings("unused")
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
