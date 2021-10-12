package xyz.wongs.drunkard.common.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.wongs.drunkard.common.annotation.RequestLimit;
import xyz.wongs.drunkard.framework.interceptor.LimitInterceptor;

/**
 * 控制访问频率，防止恶意行为，需要定义哪些服务需要控制，在类/方法 通过 注解 {@link RequestLimit}
 *
 *
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
        registry.addInterceptor(limitInterceptor).addPathPatterns("/**");
        ;
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
