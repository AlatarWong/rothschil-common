package xyz.wongs.drunkard.common.annotation;

import java.lang.annotation.*;


/**
 * 请求限制的自定义注解，
 * <ul>
 * <li>Target:注解可修饰的对象范围，ElementType.METHOD 作用于方法，ElementType.TYPE 作用于类</li>
 * <li>Retention:定义了该Annotation被保留的时间长短：某些Annotation仅出现在源代码中，而被编译器丢弃</li>
 * <li>Inherited:元注解是一个标记注解</li>
 * </ul>
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:02
 * @since 1.0.0
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    /**
     * second 秒内，最大只能请求 maxCount 次
     */
    int second() default 1;

    /**
     * 单位时间内，允许访问最大次数
     */
    int maxCount() default 1;
}
