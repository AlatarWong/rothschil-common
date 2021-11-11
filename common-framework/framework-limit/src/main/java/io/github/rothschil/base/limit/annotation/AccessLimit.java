package io.github.rothschil.base.limit.annotation;

import java.lang.annotation.*;

/**
 * 采用分布式组件 Redis 作为限流的工具
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/11 - 15:41
 * @since 1.0.0
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 默认 10次
     */
    int limit() default 10;

    /**
     * 默认 1 秒
     */
    int sec() default 1;
}
