package io.github.rothschil.base.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/** 自定义增强型 异步中的 Exception 操作
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2022/4/24 - 9:58
 * @since 1.0.0
 */
@Slf4j
public class StrengthenAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Exception message - {} , Method name - {}", ex.getMessage(),method.getName());
        for (Object param : params) {
            log.error("Parameter value - " + param);
        }
    }
}
