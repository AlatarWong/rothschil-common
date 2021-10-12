package xyz.wongs.drunkard.base.response.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xyz.wongs.drunkard.common.constant.Constants;
import xyz.wongs.drunkard.base.response.interceptor.ResponseBodyInterceptor;
import xyz.wongs.drunkard.base.response.po.ErR;
import xyz.wongs.drunkard.base.response.po.R;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;


/**
 * 统一处理响应的消息内容，默认引入的包路径是 xyz.wongs，编写过程中需要格外注意。在需要响应的内容做标记，
 * 继而在 {@link ResponseBodyInterceptor} 基础上捕获该标记，再次处理
 * 默认处理 {@link Controller},RestController.class,
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/26 - 16:23
 * @since 1.0.0
 */
@EnableWebMvc
@Configuration
@ControllerAdvice(basePackages = "xyz.wongs",annotations = {Controller.class})
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否要执行 beforeBodyWrite 方法
     *
     * @param returnType    返回类型
     * @param converterType 转换类型
     * @return boolean true:执行，false:不执行，有注解标记的时候处理返回值
     * @date 20/11/13 10:50
     */
    @Override
    public boolean supports(@Nullable MethodParameter returnType, @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(sra, "The ServletRequestAttributes must not be null");
        Assert.notNull(returnType, "The MethodParameter must not be null");
        // 先将 Controller 中含有 的 ResponseBody 注解优先甄别出来
        if(returnType.hasMethodAnnotation(ResponseBody.class)){
            return true;
        }
        HttpServletRequest request = sra.getRequest();
        RestController cller = (RestController) request.getAttribute(Constants.RESPONSE_RESULT_ANN);
        return cller!=null;
    }

    /**
     * 如果响应内容已经是 定义 {@link R} 和 {@link ErR} ，就不需要再额外处理，否则需要再包装一层
     *
     * @param body                响应消息
     * @param returnType          响应类型
     * @param selectContentType   可选内容类型
     * @param selectConverterType 选择覆盖类型
     * @param request             Request请求
     * @param response            Response响应
     * @return Object 响应内容
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-16:28
     **/
    @Override
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter returnType, @Nullable MediaType selectContentType, @Nullable Class<? extends HttpMessageConverter<?>> selectConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body instanceof R || body instanceof ErR) {
            return body;
        }
        return R.success(body);
    }
}
