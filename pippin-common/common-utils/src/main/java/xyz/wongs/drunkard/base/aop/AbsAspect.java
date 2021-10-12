package xyz.wongs.drunkard.base.aop;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import xyz.wongs.drunkard.base.aop.annotation.ApplicationLog;
import xyz.wongs.drunkard.base.aop.pojo.AppLog;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.handler.impl.QueueTaskHandler;
import xyz.wongs.drunkard.base.queue.AppLogQueue;
import xyz.wongs.drunkard.base.utils.DateUtils;
import xyz.wongs.drunkard.base.utils.IpUtils;
import xyz.wongs.drunkard.base.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;
import java.util.Enumeration;

/**
 * 定义AOP处理通用方法，引入 {@link xyz.wongs.drunkard.base.queue.AppLogQueue} 异步队列模块 和 {@link xyz.wongs.drunkard.base.handler.impl.QueueTaskHandler}
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/24 - 16:31
 * @since 1.0.0
 */
@Slf4j
public abstract class AbsAspect {

    @Autowired
    protected QueueTaskHandler queueTaskHandler;

    @Autowired
    protected AppLogQueue appLogQueue;

    protected void send2Queue(ThreadLocal<AppLog> threadLocal, Object ret, Exception e) {
        int success = 0;
        Date endTime = Date.from(Instant.now());
        AppLog appLog = threadLocal.get();
        if (null != e) {
            success = -1;
            appLog.setErr(e.getMessage());
        }
        if (null != ret) {
            appLog.setRespContent(JSON.toJSONString(ret));
        }
        appLog.setEndTime(endTime);
        appLog.setSucceed(success);
        appLog.setCost(DateUtils.getMills(appLog.getBeginTime(), endTime));
        threadLocal.remove();
        queueTaskHandler.setOperationLog(appLog);
        appLogQueue.addQueue(queueTaskHandler);
    }


    protected AppLog getOperationLog(ApplicationLog applicationLog, JoinPoint joinPoint) {
        return getOperationLog(joinPoint, applicationLog.value(), applicationLog.key());
    }


    protected AppLog getOperationLog(JoinPoint joinPoint, String businessName, String key) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        Assert.notNull(sra, "The ServletRequestAttributes must not be null");
        HttpServletRequest request = sra.getRequest();

        //获取拦截的方法名
        Date beginTime = Date.from(Instant.now());
        String methodName = joinPoint.getSignature().getName();
        // 类名
        String className = joinPoint.getTarget().getClass().getName();
        // 参数
        Object[] params = joinPoint.getArgs();
        // 参数名
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        AppLog.AppLogBuilder opt = AppLog.builder();
        String paramsValue = getParamsValue(params);
        opt.clazz(className).methodName(methodName).logName(businessName).type(key)
                .parameters(paramsValue)
                .ipAddress(IpUtils.getIpAddr(request)).url(URLUtil.getPath(request.getRequestURI()))
                .httpType(request.getMethod()).userAgent(request.getHeader(Constants.USER_TYPE))
                .beginTime(beginTime).headers(getHeader(request));

        return opt.build();
    }

    public String getHeader(HttpServletRequest request) {
        Enumeration<String> enumerates = request.getHeaderNames();
        StringBuffer sb = new StringBuffer("Header[");
        while (enumerates.hasMoreElements()) {
            String key = (String) enumerates.nextElement();
            String value = request.getHeader(key);
            sb.append(key).append(Constants.HF_COLON).append(value);
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String getParamsValue(Object[] params) {
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Object param : params) {

            if (StringUtils.isEmpty(param) | StringUtils.isBlank(param.toString())) {
                continue;
            }
            // 文件类
            if (param instanceof MultipartFile[]) {
                sb.append(" Files{");
                MultipartFile[] files = (MultipartFile[]) param;
                for (MultipartFile o : files) {
                    sb.append(o.getOriginalFilename()).append(Constants.HF_COLON);
                }
                sb.append(" }");
                return sb.toString();
            } else if (param instanceof MultipartFile) {
                sb.append(((MultipartFile) param).getOriginalFilename());
            } else if (StringUtils.isBasicType(param)) {
                sb.append(param).append(Constants.HF_COLON);
            } else {
                sb.append(JSON.toJSONString(params));
            }
        }
        return sb.toString();
    }

    /**
     * 获取 ApplicationLog 注解
     *
     * @param joinPoint 切点
     * @return ApplicationLog
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/24-16:22
     **/
    protected static ApplicationLog getApplicationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(ApplicationLog.class);
        }
        return null;
    }


    public static void main(String[] args) {
        int i = 2;
        System.out.println();
    }
}
