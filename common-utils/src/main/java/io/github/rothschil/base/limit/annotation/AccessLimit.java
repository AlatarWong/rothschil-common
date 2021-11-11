package io.github.rothschil.base.limit.annotation;

import java.lang.annotation.*;

/**
 * 采用分布式限流
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

    int limit() default 5;

    int sec() default 5;
}
