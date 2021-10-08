package xyz.wongs.drunkard.alipay.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/** 加密工具实体，定义加密属性值，属性 参考 {@link org.jasypt.encryption.pbe.PooledPBEStringEncryptor}
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/24 - 10:08
 * @since 1.0.0
 */
@Validated
@Component
@ConfigurationProperties(prefix="oss")
@PropertySource(value = "classpath:oss-sdk.properties")
public class EncryptSdk {

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
