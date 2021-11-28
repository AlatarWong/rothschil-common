package io.github.rothschil.base.aop.queue.handler.impl;

import io.github.rothschil.base.aop.entity.AppLog;
import io.github.rothschil.base.aop.queue.handler.IQueueTaskHandler;
import io.github.rothschil.base.aop.service.AbstactAppLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

//    private AbstactAppLogService abstactAppLogService;
//
//    @Autowired
//    public void setAbstactAppLogService(AbstactAppLogService abstactAppLogService) {
//        this.abstactAppLogService = abstactAppLogService;
//    }

    private AppLog appLog;

    /**
     * 实现QueueTaskHandler的处理接口
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 20/11/13 16:32
     **/
    @Override
    public void processData() {
//        abstactAppLogService.insert(appLog);
        // 可以加上事后处理逻辑....
    }

    public AppLog getOperationLog() {
        return appLog;
    }

    public void setOperationLog(AppLog appLog) {
        this.appLog = appLog;
    }
}
