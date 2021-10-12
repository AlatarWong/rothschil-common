package xyz.wongs.drunkard.oss.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 图床配置信息 主要利用阿里云的OSS存储支付过程中生成二维码，可以参考 <a href="https://www.aliyun.com/product/oss">https://www.aliyun.com/product/oss</a>
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/24 - 11:02
 * @since 1.0.0
 */
@Component
public class OssBed {

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
                ", endpoint='" + endpoint + '\'' +
                ", key='" + key + '\'' +
                ", secret='" + secret + '\'' +
                ", bucketname='" + bucketname + '\'' +
                ", directory='" + directory + '\'' +
                '}';
    }
}
