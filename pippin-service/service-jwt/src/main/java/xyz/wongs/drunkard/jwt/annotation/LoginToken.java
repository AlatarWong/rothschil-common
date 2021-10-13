package xyz.wongs.drunkard.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录表明身份注解
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2020/11/6 - 10:16
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginToken {
    /**
     * 默认 为 true，需要检查
     *
     * @return boolean
     */
    boolean required() default true;
}
