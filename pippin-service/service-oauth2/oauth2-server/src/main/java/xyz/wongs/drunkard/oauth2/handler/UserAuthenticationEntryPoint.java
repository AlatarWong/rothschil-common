package xyz.wongs.drunkard.oauth2.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.response.enums.Status;
import xyz.wongs.drunkard.oauth2.util.JSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决匿名用户访问无权限资源时的异常
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:32
 * @since 1.0.0
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        JSONUtils.writeValue(response.getOutputStream(), Status.USER_NOT_LOGIN_ERROR);
    }
}
