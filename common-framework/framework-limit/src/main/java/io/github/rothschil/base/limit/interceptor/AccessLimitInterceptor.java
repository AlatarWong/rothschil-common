package io.github.rothschil.base.limit.interceptor;

import io.github.rothschil.base.limit.annotation.AccessLimit;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.constant.Constants;
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
 * 限流拦截器，利用 K-V 键值可以存储 源IP 和 URL 做为 Key，记录当前IP/URL在单位时间内对目标资源的访问次数，
 * 去 Redis查询访问在有效时间内的访问次数
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/11 - 15:43
 * @since 1.0.0
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    /**
     * 在正式访问资源资源之前的预处理
     *
     * @param request  HttpServletRequest 请求
     * @param response HttpServletResponse 响应
     * @param handler  当前执行处理的对象
     * @return boolean
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/27-15:22
     **/
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

    /**
     * 将当前对资源的访问次数 与 资源最初定义的次数做对比，比定义的次数多则不允许访问
     *
     * @param request     HttpServletRequest 请求
     * @param response    HttpServletResponse 响应
     * @param accessLimit 资源可被访问的定义
     * @return boolean
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/27-15:19
     **/
    protected boolean biggest(HttpServletRequest request, HttpServletResponse response, AccessLimit accessLimit) {
        int limit = accessLimit.limit();
        int sec = accessLimit.sec();
        String key = IpUtils.getIpAddr(request) + request.getRequestURI();
        Integer maxLimit = redisTemplate.opsForValue().get(key);
        if (null == maxLimit) {
            redisTemplate.opsForValue().set(key, 1, sec, TimeUnit.SECONDS);
        } else if (maxLimit < limit) {
            redisTemplate.opsForValue().set(key, maxLimit + 1, sec, TimeUnit.SECONDS);
        } else {
            output(response, Status.API_REQ_MORE_THAN_SET.getMsg());
            return false;
        }
        return true;
    }

    public void output(HttpServletResponse response, String msg) {
        response.setContentType(Constants.CONTENT_TYPE);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closable(outputStream);
        }
    }

    private void closable(ServletOutputStream outputStream){
        try {
            if (null != outputStream) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
