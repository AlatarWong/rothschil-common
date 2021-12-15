package io.github.rothschil.common.queue;

import io.github.rothschil.base.aop.entity.AppLog;
import io.github.rothschil.base.aop.queue.handler.TaskHandlerble;
import org.springframework.stereotype.Component;

/**
 * 针对队列的任务处理者
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class AppLogHandler implements TaskHandlerble {

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
    public void process() {
        // 可以加上事后处理逻辑....
    }

    public AppLog getOperationLog() {
        return appLog;
    }

    public void setOperationLog(AppLog appLog) {
        this.appLog = appLog;
    }
}
