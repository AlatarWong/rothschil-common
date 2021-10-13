package xyz.wongs.drunkard.oauth2.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.oauth2.event.event.UserLoginFailedEvent;

/**
 * 用户登录失败监听器
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/27 17:01
 * @since 1.0.0
 */
@Slf4j
@Component
public class UserLoginFailedListener implements ApplicationListener<UserLoginFailedEvent> {
    @Override
    public void onApplicationEvent(UserLoginFailedEvent event) {
        log.error("----用户验证信息---faile----------------------");
    }
}
