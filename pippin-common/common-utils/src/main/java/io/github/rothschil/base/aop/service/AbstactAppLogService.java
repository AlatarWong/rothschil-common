package io.github.rothschil.base.aop.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.aop.pojo.AppLog;

/**
 * 抽象类，完成对日志信息 进行持久化的操作，现实中可以根据实际场景，对该类进行重写，达到自定义的要求
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AbstactAppLogService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstactAppLogService.class);

    public void insert(AppLog appLog) {
        LOG.error(appLog.toString());
    }

}
