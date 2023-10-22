package io.github.rothschil.base.persistence.redis;

import org.redisson.api.RLock;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/1 - 11:02
 * @since 1.0.0
 */
public interface DistributedLock {

    RLock lock(String key);

    RLock lock(String key, int timeout);

    RLock lock(String key, TimeUnit unit, int timeout);

    boolean tryLock(String key, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String key);

    void unlock(RLock lock);
}
