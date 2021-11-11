package io.github.rothschil.base.limit.interceptor;

import io.github.rothschil.base.limit.annotation.AccessLimit;
import io.github.rothschil.common.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/11 - 15:43
 * @since 1.0.0
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Assert.notNull(request, "The request must not be null");
        Assert.notNull(response, "The response must not be null");
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimit.class)) {
                // 不需要限流的则直接通过
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (null == accessLimit) {
                return true;
            }
            return biggest(request, response, accessLimit);
        }
        return true;
    }

    protected boolean biggest(HttpServletRequest request, HttpServletResponse response, AccessLimit accessLimit) {
        int limit = accessLimit.limit();
        int sec = accessLimit.sec();
        String key = IpUtils.getIpAddr(request) + request.getRequestURI();
        Integer maxLimit = redisTemplate.opsForValue().get(key);
        if (maxLimit == null) {
            redisTemplate.opsForValue().set(key, 1, sec, TimeUnit.SECONDS);
        } else if (maxLimit < limit) {
            redisTemplate.opsForValue().set(key, maxLimit + 1, sec, TimeUnit.SECONDS);
        } else {
            output(response, "请求太频繁!");
            return false;
        }
        return true;
    }

    public void output(HttpServletResponse response, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
