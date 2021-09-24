package xyz.wongs.drunkard.alipay.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 21:31
 * @version 1.0.0
 */
@Validated
@Component
@ConfigurationProperties(prefix="pay")
@PropertySource(value = "classpath:oss-pay.properties")
public class AlipayProperty {
    private String providerId;
    private String callback;
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
