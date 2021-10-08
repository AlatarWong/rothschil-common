package xyz.wongs.drunkard.alipay.model.builder;

import xyz.wongs.drunkard.alipay.util.GsonFactory;

/** 请求抽象类
 * @author <a href="https://github.com/rothschil">Sam</a>
 *
 * @date 2021/9/23 - 10:21
 * @since 1.0.0
 */
public abstract class RequestBuilder {
    private String appAuthToken;
    private String notifyUrl;

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-10:42
     * @param
     * @return boolean
     **/
    public abstract boolean validate();

    /** 获取bizContent对象，用于下一步转换为json字符串
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-10:42
     * @param
     * @return Object
     **/
    public abstract Object getBizContent();

    /** 将bizContent对象转换为json字符串
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-10:42
     * @param
     * @return String
     **/
    public String toJsonString() {
        // 使用gson将对象转换为json字符串
        return GsonFactory.getGson().toJson(this.getBizContent());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RequestBuilder{");
        sb.append("appAuthToken='").append(appAuthToken).append('\'');
        sb.append(", notifyUrl='").append(notifyUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAppAuthToken() {
        return appAuthToken;
    }

    public RequestBuilder setAppAuthToken(String appAuthToken) {
        this.appAuthToken = appAuthToken;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public RequestBuilder setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }
}
