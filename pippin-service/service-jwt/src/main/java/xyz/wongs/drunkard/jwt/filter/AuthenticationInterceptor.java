package xyz.wongs.drunkard.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.wongs.drunkard.base.response.enums.Status;
import xyz.wongs.drunkard.common.exception.DrunkardException;
import xyz.wongs.drunkard.jwt.annotation.IgnoreTokenCheck;
import xyz.wongs.drunkard.jwt.annotation.LoginToken;
import xyz.wongs.drunkard.jwt.po.User;
import xyz.wongs.drunkard.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截器，获取 Token，并验证 Token合法性
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/6 - 10:25
 * @since 1.0.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    static Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = handler instanceof HandlerMethod;
        if (!flag) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(IgnoreTokenCheck.class)) {
            IgnoreTokenCheck ignoreTokenCheck = method.getAnnotation(IgnoreTokenCheck.class);
            if (ignoreTokenCheck.required()) {
                return true;
            }
        }
        if (!method.isAnnotationPresent(LoginToken.class)) {
            return true;
        }
        LoginToken loginToken = method.getAnnotation(LoginToken.class);

        // 需要身份验证，获取Token，验证Token合法性
        if (loginToken.required()) {
            return requiredLogin(request);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    protected boolean requiredLogin(HttpServletRequest request){
        String TOKEN = "token";
        String token = request.getHeader(TOKEN);
        // 没有 Token
        if (ObjectUtils.isEmpty(token)) {
            throw new DrunkardException(Status.USER_NOT_LOGGED_IN);
        }
        try {
            // Token解析出 ID标识
            String id = JWT.decode(token).getAudience().get(0);
            User user = userService.getUserById(id);
            if (null == user) {
                throw new DrunkardException(Status.USER_NOT_LOGIN_ERROR);
            }
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getName())).build();
            jwtVerifier.verify(token);
        } catch (JWTDecodeException e) {
            throw new DrunkardException(Status.TOKEN_EXPIRED);
        } catch (JWTVerificationException e) {
            throw new DrunkardException(Status.USER_SIGN_VERIFY_NOT_COMPLIANT);
        }
        return true;
    }
}
