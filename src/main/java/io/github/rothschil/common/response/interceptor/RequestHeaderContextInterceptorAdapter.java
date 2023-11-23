package io.github.rothschil.common.response.interceptor;

import io.github.rothschil.common.base.vo.BaseReqVo;
import io.github.rothschil.common.constant.Constant;
import io.github.rothschil.common.utils.IpUtil;
import io.github.rothschil.common.utils.UserTransmittableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对请求进行统一处理
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 */
@Component
@Slf4j
public class RequestHeaderContextInterceptorAdapter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        initHeaderContext(request);
        return super.preHandle(request, response, handler);
    }

    /**
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param request
     **/
    private void initHeaderContext(HttpServletRequest request){
        String origClientIp = IpUtil.getIpAddr(request);
        String userAgent = request.getHeader(Constant.USER_AGENT);
        BaseReqVo reqVo = BaseReqVo.builder().origClientIp(origClientIp).userAgent(userAgent).build();
        UserTransmittableUtils.set(reqVo);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserTransmittableUtils.clear();
        super.postHandle(request, response, handler, modelAndView);
    }
}
