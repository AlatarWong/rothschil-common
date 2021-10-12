package xyz.wongs.drunkard.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @Description 忽略Token验证注解
 * 
 * @date 2020/11/6 - 10:16
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreTokenCheck {

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @Description 默认 为 true，需要检查
     * @date 2020/11/7-20:10
     * @param null
     * @return null
     **/
    boolean required() default true;
}
