package io.github.rothschil.oauth2.event.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

/**
 * 定义用户登录失败事件
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/7/31 10:46
 * @since 1.0
 */
public class UserLoginFailedEvent extends ApplicationEvent {
    public UserLoginFailedEvent(Authentication authentication) {
        super(authentication);
    }
}
