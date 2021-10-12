package xyz.wongs.drunkard.base.queue.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.aop.pojo.AppLog;
import xyz.wongs.drunkard.base.aop.service.OperationLogService;
import xyz.wongs.drunkard.base.queue.handler.IQueueTaskHandler;

/**
 * 针对队列的任务处理者
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Component
public class QueueTaskHandler implements IQueueTaskHandler {

    @Autowired
    private OperationLogService operationLogService;

    private AppLog appLog;

    /**
     * 实现QueueTaskHandler的处理接口
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 20/11/13 16:32
     **/
    @Override
    public void processData() {
        operationLogService.insert(appLog);
        // 可以加上事后处理逻辑....
    }

    public AppLog getOperationLog() {
        return appLog;
    }

    public void setOperationLog(AppLog appLog) {
        this.appLog = appLog;
    }
}
