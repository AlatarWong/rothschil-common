package io.github.rothschil.base.aop.service;


import io.github.rothschil.base.aop.entity.AppLog;

/**
 * 抽象类，完成对日志信息 进行持久化的操作，现实中可以根据实际场景，对该类进行重写，达到自定义的要求
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 20/11/18 11:04
 * @since 1.0.0
 */
public abstract class AbstactAppLogService {

    public abstract void insert(AppLog appLog);

}
