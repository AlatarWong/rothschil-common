package xyz.wongs.drunkard.framework.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.wongs.drunkard.common.utils.ServletUtils;
import xyz.wongs.drunkard.common.annotation.RepeatSubmit;
import xyz.wongs.drunkard.common.core.domain.AjaxResult;
import xyz.wongs.drunkard.common.json.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截器，用于防止表单重复提交
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 21:11
 * @since 1.0.0
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    AjaxResult ajaxResult = AjaxResult.error("不允许重复提交，请稍后再试");
                    ServletUtils.renderString(response, JSON.marshal(ajaxResult));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request 请求
     * @return boolean 是否重复
     * @throws Exception 异常
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;
}
