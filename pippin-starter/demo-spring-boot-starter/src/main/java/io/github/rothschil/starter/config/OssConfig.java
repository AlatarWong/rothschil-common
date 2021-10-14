package io.github.rothschil.starter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import io.github.rothschil.starter.prop.OssProperties;
import io.github.rothschil.starter.service.OssService;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 10:07
 * @since 1.0.0
 */
@EnableConfigurationProperties(OssProperties.class)
public class OssConfig {

    @Bean
    public OssService ossService(){
        return new OssService();
    }
}
