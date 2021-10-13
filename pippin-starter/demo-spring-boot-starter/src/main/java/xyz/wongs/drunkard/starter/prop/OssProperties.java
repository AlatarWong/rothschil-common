package xyz.wongs.drunkard.starter.prop;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 10:03
 * @since 1.0.0
 */
@ToString
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    private String endpoint;

    private String key;

    private String secret;

    private String bucketname;

    private String directory;
}
