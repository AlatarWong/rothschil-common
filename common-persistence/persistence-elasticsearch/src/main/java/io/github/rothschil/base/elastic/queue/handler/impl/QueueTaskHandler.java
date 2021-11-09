package io.github.rothschil.base.elastic.queue.handler.impl;


import io.github.rothschil.base.elastic.bo.PendingData;
import io.github.rothschil.base.elastic.queue.handler.IQueueTaskHandler;
import io.github.rothschil.base.elastic.service.ElasticIndexManger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 针对队列的任务 消费者
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/1 - 21:04
 * @since 1.0.0
 */
@Slf4j
@Component
@Scope("prototype")
public class QueueTaskHandler implements IQueueTaskHandler {

    private PendingData pendingData;

    private ElasticIndexManger elasticIndexManger;

    /**
     * 异步处理数据的核心方法
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2017/8/18-16:26
     **/
    @Override
    public void processData() {
        try {
            elasticIndexManger.batch(pendingData.getLists(), pendingData.getIndexName());
        } catch (Exception e) {
            log.error("[失败重送队列异常]");
        }

    }

    @Autowired
    public void setElasticIndexManger(ElasticIndexManger elasticIndexManger) {
        this.elasticIndexManger = elasticIndexManger;
    }

    public void setPendingData(PendingData pendingData) {
        this.pendingData = pendingData;
    }
}
