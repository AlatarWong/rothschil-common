package io.github.rothschil.base.config;

import io.github.rothschil.base.config.exception.StrengthenAsyncUncaughtExceptionHandler;
import io.github.rothschil.common.utils.thread.ThreadPoolsUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import io.github.rothschil.common.utils.thread.Threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池任务配置
 * <ui>
 *     <li>对外暴露 {@link ThreadPoolTaskExecutor} 和 {@link ScheduledExecutorService} 分别执行 自定义线程池任务 和 带有延迟任务的线程池 ，在 {@link Async} 中指定名称即可</li>
 *     <li>提供默认的线程池构造方式，不需要指定线程池名称</li>
 * </ui>
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/12/2 15:19
 * @since 1.0.0
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {

    /**
     * 定义线程池任务
     *
     * @return ThreadPoolTaskExecutor
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-8:37
     **/
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return doBuilder();
    }


    /**
     * 执行周期性或定时任务
     *
     * @return ScheduledExecutorService
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/15-8:36
     **/
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(ThreadPoolsUtil.getCorePoolSize(0),
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }

    @Override
    public Executor getAsyncExecutor() {
        return doBuilder();
    }


    protected ThreadPoolTaskExecutor doBuilder(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ThreadPoolsUtil.getCorePoolSize(0));
        executor.setMaxPoolSize(0XC8);
        executor.setQueueCapacity(0X3E8);
        executor.setKeepAliveSeconds(0X3C);
        // Set Thread name Prefix and Group name
        executor.setThreadNamePrefix("AsyncThread-");
        executor.setThreadGroupName("AsyncGroup");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(ThreadPoolsUtil.getRejectedExecutionHandler());
        // 所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }

    /**
     * 自定义捕获异步操作中的异常
     *
     * @return AsyncUncaughtExceptionHandler
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2022/4/24-9:55
     **/
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new StrengthenAsyncUncaughtExceptionHandler();
    }
}
