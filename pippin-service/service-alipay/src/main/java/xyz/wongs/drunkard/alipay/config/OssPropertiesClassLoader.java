package xyz.wongs.drunkard.alipay.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.wongs.drunkard.alipay.pojo.OssBed;
import xyz.wongs.drunkard.alipay.pojo.OssPorperty;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 15:18
 * @version 1.0.0
 */
@Configuration
public class OssPropertiesClassLoader {

    private static final Logger LOG = LoggerFactory.getLogger(OssPropertiesClassLoader.class);

    @Autowired
    private OssPorperty ossPorperty;

    @Autowired
    private OssBed ossBed;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(ossPorperty.getStorehouse());
        config.setKeyObtentionIterations(ossPorperty.getIterations());
        config.setPoolSize(ossPorperty.getSize());
        config.setProviderName(ossPorperty.getProviderName());
        config.setSaltGeneratorClassName(ossPorperty.getSaltGeneratorClassName());
        config.setIvGeneratorClassName(ossPorperty.getLvGeneratorClassName());
        config.setStringOutputType(ossPorperty.getStringOutputType());
        encryptor.setConfig(config);
        return encryptor;
    }

}
