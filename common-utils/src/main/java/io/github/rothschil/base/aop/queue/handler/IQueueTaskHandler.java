package io.github.rothschil.base.aop.queue.handler;

/**
 * 针对队列的任务处理者
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/13 16:18
 * @since 1.0.0
 */
public interface IQueueTaskHandler {

    /**
     * 实现QueueTaskHandler的处理接口
     *
     * @date 20/11/19 17:09
     */
    void processData();
}
