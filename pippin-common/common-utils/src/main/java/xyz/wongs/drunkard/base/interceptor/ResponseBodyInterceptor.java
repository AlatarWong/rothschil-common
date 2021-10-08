package xyz.wongs.drunkard.base.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.message.annoation.Body;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/** 定义统一响应内容的拦截器，为内容进行设置 {@link Constants.RESPONSE_RESULT_ANN }，可以识别和兼容 方法 和 类的 Annotation 注解
 * @author WCNGS@QQ.COM
 * @date 20/10/30 22:08
 * @since 1.0.0
 */
@Component
public class ResponseBodyInterceptor implements HandlerInterceptor {

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-16:35
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler HandlerMethod
     * @return boolean
     **/
    @Override
    public boolean preHandle(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        Assert.notNull(request,"The request must not be null");
        Assert.notNull(response,"The response must not be null");
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(Body.class)) {
                request.setAttribute(Constants.RESPONSE_RESULT_ANN, clazz.getAnnotation(Body.class));
            } else if (method.isAnnotationPresent(Body.class)) {
                request.setAttribute(Constants.RESPONSE_RESULT_ANN, method.getAnnotation(Body.class));
            }
        }
        return true;
    }

}
