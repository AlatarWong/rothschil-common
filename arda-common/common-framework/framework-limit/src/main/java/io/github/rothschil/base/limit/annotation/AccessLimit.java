package io.github.rothschil.base.limit.annotation;

import java.lang.annotation.*;

/**
 * 采用分布式组件 Redis 作为限流的工具
 * 请求限制的自定义注解，
 * <ul>
 * <li>Target:注解可修饰的对象范围，ElementType.METHOD 作用于方法，ElementType.TYPE 作用于类</li>
 * <li>Retention:定义了该Annotation被保留的时间长短：某些Annotation仅出现在源代码中，而被编译器丢弃</li>
 * <li>Inherited:元注解是一个标记注解</li>
 * </ul>
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
