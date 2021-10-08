package xyz.wongs.drunkard.base.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.aop.pojo.AppLog;
import xyz.wongs.drunkard.base.aop.service.OperationLogService;
import xyz.wongs.drunkard.base.handler.IQueueTaskHandler;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
*/
@Component
public class QueueTaskHandler implements IQueueTaskHandler {

    private static final Logger LOG = LoggerFactory.getLogger(QueueTaskHandler.class);

    @Autowired
    private OperationLogService operationLogService;

    private AppLog appLog;

    /**
     * @Author <a href="https://github.com/rothschil">Sam</a>
     * @Description 这里也就是我们实现QueueTaskHandler的处理接口
     * @Date 20/11/13 16:32
     * @Param
     **/
    @Override
    public void processData() {
        operationLogService.insert(appLog);
        // 可以加上自己的事后处理逻辑....
    }

    public AppLog getOperationLog() {
        return appLog;
    }

    public void setOperationLog(AppLog appLog) {
        this.appLog = appLog;
    }
}
