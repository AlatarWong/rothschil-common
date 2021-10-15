package io.github.rothschil.base.response.annoation;

import io.github.rothschil.base.response.handler.ResponseBodyHandler;
import io.github.rothschil.base.response.interceptor.ResponseBodyInterceptor;

import java.lang.annotation.*;

/**
 * 用于定义统一响应内容的格式，在标签上加入该注解，即可被识别，不推荐使用该模式，可以参看 {@link ResponseBodyHandler}
 * 和 {@link ResponseBodyInterceptor} 已经可以满足对 Spring MVC 层面的响应做统一处理
 *
 * @author WCNGS@QQ.COM
 * @date 19/8/30 21:57
 * @since 1.0.0
 */
@Deprecated
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Body {
}
