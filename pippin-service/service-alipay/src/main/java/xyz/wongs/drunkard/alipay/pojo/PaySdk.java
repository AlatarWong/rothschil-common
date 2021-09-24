//package xyz.wongs.drunkard.alipay.pojo;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.annotation.Validated;
//
///**
// * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
// * @github <a>https://github.com/rothschil</a>
// * @date 2021/9/24 - 21:31
// * @version 1.0.0
// */
//@Validated
//@Component
//@ConfigurationProperties(prefix="pay2")
//@PropertySource(value = "classpath:oss-pay2.properties")
//public class PaySdk {
//
//    private String openApiDomain;
//    private String mcloudApiDomain;
//    private String pid;
//    private String appid;
//    private String privateKey;
//    private String publicKey;
//    private String alipayPublicKey;
//    private String signType;
//    private int maxQueryRetry;
//    private long queryDuration;
//    private int maxCancelRetry;
//    private long cancelDuration;
//    private long heartbeatDelay;
//    private long heartbeatDuration;
//
//    public String getOpenApiDomain() {
//        return openApiDomain;
//    }
//
//    public void setOpenApiDomain(String openApiDomain) {
//        this.openApiDomain = openApiDomain;
//    }
//
//    public String getMcloudApiDomain() {
//        return mcloudApiDomain;
//    }
//
//    public void setMcloudApiDomain(String mcloudApiDomain) {
//        this.mcloudApiDomain = mcloudApiDomain;
//    }
//
//    public String getPid() {
//        return pid;
//    }
//
//    public void setPid(String pid) {
//        this.pid = pid;
//    }
//
//    public String getAppid() {
//        return appid;
//    }
//
//    public void setAppid(String appid) {
//        this.appid = appid;
//    }
//
//    public String getPrivateKey() {
//        return privateKey;
//    }
//
//    public void setPrivateKey(String privateKey) {
//        this.privateKey = privateKey;
//    }
//
//    public String getPublicKey() {
//        return publicKey;
//    }
//
//    public void setPublicKey(String publicKey) {
//        this.publicKey = publicKey;
//    }
//
//    public String getAlipayPublicKey() {
//        return alipayPublicKey;
//    }
//
//    public void setAlipayPublicKey(String alipayPublicKey) {
//        this.alipayPublicKey = alipayPublicKey;
//    }
//
//    public String getSignType() {
//        return signType;
//    }
//
//    public void setSignType(String signType) {
//        this.signType = signType;
//    }
//
//    public int getMaxQueryRetry() {
//        return maxQueryRetry;
//    }
//
//    public void setMaxQueryRetry(int maxQueryRetry) {
//        this.maxQueryRetry = maxQueryRetry;
//    }
//
//    public long getQueryDuration() {
//        return queryDuration;
//    }
//
//    public void setQueryDuration(long queryDuration) {
//        this.queryDuration = queryDuration;
//    }
//
//    public int getMaxCancelRetry() {
//        return maxCancelRetry;
//    }
//
//    public void setMaxCancelRetry(int maxCancelRetry) {
//        this.maxCancelRetry = maxCancelRetry;
//    }
//
//    public long getCancelDuration() {
//        return cancelDuration;
//    }
//
//    public void setCancelDuration(long cancelDuration) {
//        this.cancelDuration = cancelDuration;
//    }
//
//    public long getHeartbeatDelay() {
//        return heartbeatDelay;
//    }
//
//    public void setHeartbeatDelay(long heartbeatDelay) {
//        this.heartbeatDelay = heartbeatDelay;
//    }
//
//    public long getHeartbeatDuration() {
//        return heartbeatDuration;
//    }
//
//    public void setHeartbeatDuration(long heartbeatDuration) {
//        this.heartbeatDuration = heartbeatDuration;
//    }
//}
