package io.github.rothschil.base.property.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import io.github.rothschil.base.property.PropConfig;

/**
 * 配置文件监听器，用来加载自定义配置文件，需要在 {@link SpringApplication} 中注册该监听器。
 * <hr>
 * <p>
 *      eg : application.addListeners(new PropListener("xx.properties"));
 *      其中  xx.properties 为 classpath下的 Properties文件
 * </p>
 *
 *  实际使用可以参考 {@link PropConfig} 中定义的静态方法
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/9/24 - 21:58
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class PropListener implements ApplicationListener<ApplicationStartedEvent> {

    private final String propertyFileName;

    public PropListener(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        PropConfig.loadAllProperties(propertyFileName);
    }
}
