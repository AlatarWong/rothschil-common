package xyz.wongs.drunkard.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.wongs.drunkard.base.response.interceptor.ResponseBodyInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 *  <li>将系统自带的JSON序列化改为 FastJson</li>
 *  <li>将统一处理响应消息拦截器加入到应用的上下文中</li>
 * </ul>
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:13
 * @since 1.0.0
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ResponseBodyInterceptor responseBodyInterceptor;

    /** 自定义消息转换器
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-17:07
     * @param converters 转换器
     **/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 清除默认 Json 转换器
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        // 配置 FastJson
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        config.setCharset(StandardCharsets.UTF_8);

        // 添加 FastJsonHttpMessageConverter
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(config);


        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(converter);
        // 添加 StringHttpMessageConverter，解决中文乱码问题
//        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//        converters.add(messageConverter);
//        converters.add(0, new FastJsonHttpMessageConverter());
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseBodyInterceptor).addPathPatterns("/**").excludePathPatterns("/webjars/**", "/static/**");;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**") .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico");
    }
}
