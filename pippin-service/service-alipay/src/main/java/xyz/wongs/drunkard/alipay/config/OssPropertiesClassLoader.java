package xyz.wongs.drunkard.alipay.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @description //TODO
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 15:18
 * @version 1.0.0
 */
@Configuration
public class OssPropertiesClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(OssPropertiesClassLoader.class);

    private Map<String, String> versionProperties = new HashMap<>(16);

    private void init(String name) {
        try {
            Properties properties = new Properties();
            InputStream in = OssPropertiesClassLoader.class.getClassLoader().getResourceAsStream(name + ".properties");
            properties.load(in);
            logger.warn(" load {}.properties ", name);
            for (String keyName : properties.stringPropertyNames()) {
                String value = properties.getProperty(keyName);
                versionProperties.put(keyName, value);
                logger.warn("{}.properties---------key:{},value:{}", name, keyName, value);
            }
            logger.warn("{}.properties Parameters loaded", name);
        } catch (IOException ignored) {
            logger.error(" load {}.properties Exception", name);
        }

    }

    @Bean(name = "aliossMap")
    public Map<String, String> aliossMap() {
        init("alioss");
        return versionProperties;
    }


}
