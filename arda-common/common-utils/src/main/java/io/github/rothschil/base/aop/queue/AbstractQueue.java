package io.github.rothschil.base.aop.queue;

import io.github.rothschil.base.aop.queue.handler.TaskHandlerble;
import io.github.rothschil.common.utils.thread.PippinThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 抽象队列模式，为体现 "开闭"的设计模式，此处仅仅定义队列的通用方法，借鉴策略的思想，
 * 将所有关于队列中 Handler 具体要处理的任务，则交由派生类去实现 接口 {@link TaskHandlerble} 并重写 方法
 * process，达到满足不同业务场景的需求
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/12/4 - 17:45
 * @since 1.0.0
 */
@Component
public class AbstractQueue<T extends TaskHandlerble> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractQueue.class);

    private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>(200);
    private ThreadPoolExecutor pool = getThread();

    /**
     * 检查服务是否运行
     */
    private volatile boolean running = true;

    /**
     * 线程状态
     */
    private Future<?> threadStatus = null;

    /**
     * 上下文加载之前处理，且只被执行一次
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:24
     **/
    @PostConstruct
    public void init() {
        threadStatus = pool.submit(() -> {
            while (running) {
                try {
                    if (!queue.isEmpty()) {
                        LOG.error("QUEUE IS NOT EMPTY");
                        T handler = (T) queue.take();
                        handler.process();
                    }
                } catch (InterruptedException e) {
                    LOG.error("服务停止，退出", e);
                    running = false;
                }
            }
        });
    }


    /**
     * 在对象被销毁之前的处理逻辑
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    @PreDestroy
    public void destroys() {
        running = false;
        LOG.error("服务停止，对象销毁");
        pool.shutdownNow();
    }

    /**
     * 当线程池被关闭后，重启初始化线程池服务
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public void activeService() {
        running = true;
        if (pool.isShutdown()) {
            init();
            LOG.info("线程池关闭，重新初始化线程池及任务");
        }
        if (threadStatus.isDone()) {
            init();
            LOG.info("线程池任务结束!");
        }
    }

    /**
     * 加入队列
     *
     * @param taskHandler 处理队列中实例
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public void addQueue(T taskHandler) {
        if (!running) {
            LOG.warn("service is stop");
            return;
        }
        boolean isFull = queue.offer(taskHandler);
        if (!isFull) {
            LOG.warn("添加任务到队列失败");
        }
    }

    /**
     * 判断队列中的实例是否为空
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public boolean empty() {
        return queue.isEmpty();
    }

    /**
     * 手工中断，防止占用资源过多
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public void interrupted() {
        if (!running) {
            LOG.warn("service is stop");
        } else {
            queue.clear();
            running = false;
        }
    }

    /**
     * 获取当前队列中的元素个数
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * 队列运行状态
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public boolean isRunning() {
        return running;
    }

    /**
     * 队列运行状态
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public Future<?> threadStatus() {
        return threadStatus;
    }

    private ThreadPoolExecutor getThread() {
        ThreadFactory threadFactory = new PippinThreadFactory("QUEUE");
        RejectedExecutionHandler policy = new ThreadPoolExecutor.AbortPolicy();
        return new ThreadPoolExecutor(1, 6, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(300), threadFactory, policy);
    }

}
