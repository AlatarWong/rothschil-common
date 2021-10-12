package xyz.wongs.drunkard.base.message.annoation;

import java.lang.annotation.*;

/**
 * 响应的内容实体
 *
 * @author WCNGS@QQ.COM
 * @date 20/10/30 21:57
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Body {
}
