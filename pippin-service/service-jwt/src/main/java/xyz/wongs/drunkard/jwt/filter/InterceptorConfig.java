package xyz.wongs.drunkard.jwt.filter;

/**
 * @Author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2021/7/6 - 10:49
 * @since 1.0.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    static Logger LOG = LoggerFactory.getLogger(InterceptorConfig.class);


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
