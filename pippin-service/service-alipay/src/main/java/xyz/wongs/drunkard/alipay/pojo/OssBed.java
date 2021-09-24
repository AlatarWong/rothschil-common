package xyz.wongs.drunkard.alipay.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 11:02
 * @version 1.0.0
 */
@Component
public class OssBed {

    @Value("${alipay.callback.url}")
    private String alipayCallbackUrl;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.key}")
    private String key;

    @Value("${oss.secret}")
    private String secret;

    @Value("${oss.bucketname}")
    private String bucketname;

    @Value("${oss.directory}")
    private String directory;

    public String getAlipayCallbackUrl() {
        return alipayCallbackUrl;
    }

    public void setAlipayCallbackUrl(String alipayCallbackUrl) {
        this.alipayCallbackUrl = alipayCallbackUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getBucketname() {
        return bucketname;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "OssBed{" +
                "alipayCallbackUrl='" + alipayCallbackUrl + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", key='" + key + '\'' +
                ", secret='" + secret + '\'' +
                ", bucketname='" + bucketname + '\'' +
                ", directory='" + directory + '\'' +
                '}';
    }
}
