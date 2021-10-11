package xyz.wongs.drunkard.base.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.wongs.drunkard.base.constant.Constants;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义统一响应内容的拦截器，为内容进行设置 {@link Constants.RESPONSE_RESULT_ANN }，可以识别和兼容 方法 和 类的 Annotation 注解
 *
 * @author WCNGS@QQ.COM
 * @date 20/10/30 22:08
 * @since 1.0.0
 */
@Component
public class ResponseBodyInterceptor implements HandlerInterceptor {

    /** Handler 处理 之前的预处理
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  HandlerMethod
     * @return boolean
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-16:35
     **/
    @Override
    public boolean preHandle(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        Assert.notNull(request, "The request must not be null");
        Assert.notNull(response, "The response must not be null");
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            if (clazz.isAnnotationPresent(RestController.class)) {
                request.setAttribute(Constants.RESPONSE_RESULT_ANN, clazz.getAnnotation(RestController.class));
            }

        }
        return true;
    }


    /** 请求结束执行 只有在 {@link ResponseBodyInterceptor.preHandle} 结果为 True，才执行
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/11-11:16
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param handler   handler
     * @param modelAndView  modelAndView
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    /**
     *
     * 视图渲染完成后才执行，但是需要满足条件：
     *
     * 请求结束执行 只有在 {@link ResponseBodyInterceptor.preHandle} 结果为 True，才执行
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/11-11:17
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param handler   handler
     * @param ex    ex
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
