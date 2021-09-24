package xyz.wongs.drunkard.alipay.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 10:08
 * @version 1.0.0
 */
@Validated
@Component
@ConfigurationProperties(prefix="oss")
@PropertySource(value = "classpath:aliyun-sdk.properties")
public class OssPorperty {

    private String storehouse;
    private String iterations;
    private String size;
    private String providerName;
    private String saltGeneratorClassName;
    private String lvGeneratorClassName;
    private String stringOutputType;


    public String getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(String storehouse) {
        this.storehouse = storehouse;
    }

    public String getIterations() {
        return iterations;
    }

    public void setIterations(String iterations) {
        this.iterations = iterations;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getSaltGeneratorClassName() {
        return saltGeneratorClassName;
    }

    public void setSaltGeneratorClassName(String saltGeneratorClassName) {
        this.saltGeneratorClassName = saltGeneratorClassName;
    }

    public String getLvGeneratorClassName() {
        return lvGeneratorClassName;
    }

    public void setLvGeneratorClassName(String lvGeneratorClassName) {
        this.lvGeneratorClassName = lvGeneratorClassName;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }
}
