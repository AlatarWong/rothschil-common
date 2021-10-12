package xyz.wongs.drunkard.alipay.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 *
 * @date 2018/4/24 - 21:31
 * @since 1.0.0
 */
@Validated
@Component
@ConfigurationProperties(prefix="pay")
@PropertySource(value = "classpath:oss-pay.properties")
public class AlipayProperty {

    /**
     * 系统商编号
     */
    private String providerId;

    /**
     * 回调地址
     */
    private String callback;

    /**
     * 定义支付超时时间，如5m
     */
    private String timeoutExpress;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }
}
