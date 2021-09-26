package xyz.wongs.drunkard.alipay.model;

import com.google.gson.annotations.SerializedName;

/** 扩展信息
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2021/9/23 - 9:58
 * @version 1.0.0
 */
public class ExtendParams {

    /**
     * 系统商编号
     */
    @SerializedName("sys_service_provider_id")
    private String sysServiceProviderId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExtendParams{");
        sb.append("sysServiceProviderId='").append(sysServiceProviderId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getSysServiceProviderId() {
        return sysServiceProviderId;
    }

    public ExtendParams setSysServiceProviderId(String sysServiceProviderId) {
        this.sysServiceProviderId = sysServiceProviderId;
        return this;
    }
}
