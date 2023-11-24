package io.github.rothschil.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class CustomBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CustomBeanPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Executor) {
            Executor executor = (Executor) bean;
            if (TtlExecutors.isTtlWrapper(executor)) {
                logger.info("Executor[{}] is already wrapped by TTLExecutors", executor);
                return executor;
            }
            logger.info("Executor[{}] will be wrapped by TTLExecutors", executor);
            return TtlExecutors.getTtlExecutor(executor);
        }
        return bean;
    }
}
