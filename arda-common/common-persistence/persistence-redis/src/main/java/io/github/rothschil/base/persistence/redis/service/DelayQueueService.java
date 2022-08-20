package io.github.rothschil.base.persistence.redis.service;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 定义延迟队列
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2020/11/1 - 21:04
 * @since 1.0.0
 */
@Component
public class DelayQueueService {

    private static Logger LOG = LoggerFactory.getLogger(DelayQueueService.class);

    private RedissonClient redissonClient;

    public DelayQueueService() {

    }

    public DelayQueueService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 向队列的最后添加元素
     *
     * @param value     队列值
     * @param delay     延迟时间
     * @param timeUnit  延迟时间的单位
     * @param queueCode 队列键
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2020/11/15-14:51
     **/
    public <T> void addDelayQueue(T value, long delay, TimeUnit timeUnit, String queueCode) {
        try {
            RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
            RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(value, delay, timeUnit);
            if (LOG.isInfoEnabled()) {
                LOG.info("[Add queue successfully] , Queue Key is {} ,Queue Value is {}, Delay {}", queueCode, value, delay);
            }
        } catch (Exception e) {
            LOG.error("[Failed to add queue] msg= {}", e.getMessage());
            throw new RuntimeException("[Failed to add queue]");
        }

    }

    /**
     * 根据队列键获取数据
     *
     * @param queueCode 队列键
     * @return T
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/15-15:00
     **/
    public <T> T getDelayQueue(String queueCode) throws InterruptedException {
        RBlockingDeque<Map> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        return (T) blockingDeque.take();
    }
}
