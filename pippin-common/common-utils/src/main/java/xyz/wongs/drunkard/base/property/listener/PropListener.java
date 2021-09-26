package xyz.wongs.drunkard.base.property.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import xyz.wongs.drunkard.base.property.PropConfig;

/** 配置文件监听器，用来加载自定义配置文件
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/24 - 21:58
 * @version 1.0.0
 */
public class PropListener implements ApplicationListener<ApplicationStartedEvent> {

    private String propertyFileName;

    public PropListener(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        PropConfig.loadAllProperties(propertyFileName);
    }
}
