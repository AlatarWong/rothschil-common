package io.github.rothschil.base.persistence.redis.handle;

/**
 * 延迟队列执行器
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/1 - 15:08
 * @since 1.0.0
 */
public interface DelayQueueHandle<T> {

    void execute(T t);
}
