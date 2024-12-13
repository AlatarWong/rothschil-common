package io.github.rothschil.common.base.persistence.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.rothschil.common.constant.Constant;
import lombok.Getter;
import lombok.Setter;
import io.github.rothschil.common.config.prop.Global;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a>https://github.com/rothschil</a>
 */
public abstract class AbstractEntity<ID extends Serializable> extends BaseEntity<ID> {

    @Setter
    /*** 数据库类型 */
    @JSONField(serialize = false)
    private String dtype;

    /**
     * 搜索值
     */
    @Setter
    @Getter
    private String searchValue;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Setter
    @Getter
    private Date createTime;
    /**
     * 创建者
     */
    @Setter
    @Getter
    private String createBy;


    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Setter
    @Getter
    private Date updateTime;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    public String getDtype() {
        return Global.getConfig(Constant.DB_TYPE);
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

}
