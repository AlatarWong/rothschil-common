package io.github.rothschil.queue.handler;

import io.github.rothschil.base.aop.queue.handler.TaskHandlerble;
import io.github.rothschil.domain.entity.NgxLog;
import io.github.rothschil.domain.service.NgxLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2022/1/12 - 15:20
 * @since 1.0.0
 */
@Component
public class NgxLogHandler implements TaskHandlerble {

    public NgxLogService ngxLogService;

    private List<NgxLog> logList;

    @Override
    public void process() {
        ngxLogService.insertBatchByOn(logList) ;
    }

    public NgxLogHandler(){}


    public void setLogList(List<NgxLog> logList) {
        this.logList = logList;
    }

    @Autowired
    public void setNgxLogService(NgxLogService ngxLogService) {
        this.ngxLogService = ngxLogService;
    }

}
