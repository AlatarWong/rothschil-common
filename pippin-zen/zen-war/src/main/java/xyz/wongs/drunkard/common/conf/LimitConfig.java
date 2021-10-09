package xyz.wongs.drunkard.common.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.wongs.drunkard.framework.limit.interceptor.LimitInterceptor;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 09:59
 * @since 1.0.0
*/
@Slf4j
@Configuration
public class LimitConfig implements WebMvcConfigurer {

    @Autowired
    private LimitInterceptor limitInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(limitInterceptor).addPathPatterns("/**");;
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
