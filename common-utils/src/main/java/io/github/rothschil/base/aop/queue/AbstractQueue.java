package io.github.rothschil.base.aop.queue;

import io.github.rothschil.base.aop.queue.handler.TaskHandlerble;
import io.github.rothschil.common.utils.thread.ThreadPoolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 抽象队列模式，为体现 "开闭"的设计模式，此处仅仅定义队列的通用方法，借鉴策略的思想，
 * 将所有关于队列中 Handler 具体要处理的任务，则交由派生类去实现 接口 {@link TaskHandlerble} 并重写 方法
 * process，达到满足不同业务场景的需求
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/12/4 - 17:45
 * @since 1.0.0
 */
public abstract class AbstractQueue<T extends TaskHandlerble> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractQueue.class);


    private LinkedBlockingQueue<T> queue;

    public AbstractQueue(){

    }

    public AbstractQueue(LinkedBlockingQueue<T> queue){
        this.queue=queue;
    }

    /**
     * 检查服务是否运行
     */
    private volatile boolean running = true;

    /**
     * 线程状态
     */
    private Future<?> threadStatus = null;

    private final ThreadPoolExecutor threadPoolExecutor = getThread();



    /**
     * 上下文加载之前处理，且只被执行一次
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:24
     **/
    @PostConstruct
    public void init() {
        threadStatus = threadPoolExecutor.submit(() -> {
            while (running) {
                try {
                    // 队列中不存在元素 则不处理
                    if (!queue.isEmpty()) {
                        T handler = (T)queue.take();
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
        threadPoolExecutor.shutdownNow();
    }

    /**
     * 当线程池被关闭后，重启初始化线程池服务
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    public void activeService() {
        running = true;
        if (threadPoolExecutor.isShutdown()) {
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
     * 获取定义的线程池
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:21
     **/
    private ThreadPoolExecutor getThread() {
        return ThreadPoolsUtil.doCreate(3, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.getClass().getName());
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
        } else{
            queue.clear();
            running=false;
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

}
