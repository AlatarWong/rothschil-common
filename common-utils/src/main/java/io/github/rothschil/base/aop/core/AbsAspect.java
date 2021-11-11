package io.github.rothschil.base.aop.core;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import io.github.rothschil.base.aop.annotation.ApplicationLog;
import io.github.rothschil.base.aop.entity.AppLog;
import io.github.rothschil.base.aop.queue.AppLogQueue;
import io.github.rothschil.base.aop.queue.handler.impl.QueueTaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.github.rothschil.common.constant.Constants;
import io.github.rothschil.common.utils.DateUtils;
import io.github.rothschil.common.utils.IpUtils;
import io.github.rothschil.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

/**
 * 定义AOP处理通用方法，引入 {@link AppLogQueue} 异步队列模块 和 {@link QueueTaskHandler}
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/24 - 16:31
 * @since 1.0.0
 */
@Slf4j
public abstract class AbsAspect {

    /**
     * 队列中定义的处理
     */
    @Autowired
    protected QueueTaskHandler queueTaskHandler;

    /**
     * 日志处理的异步队列
     */
    @Autowired
    protected AppLogQueue appLogQueue;

    /**
     * 发送到队列中用于异步
     *
     * @param threadLocal 线程变量
     * @param ret         响应内容
     * @param e           异常信息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/12-11:47
     **/
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


    /**
     * 获取封装 {@link AppLog} 的实例
     *
     * @param applicationLog 实例
     * @param joinPoint      切入点
     * @return AppLog
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:14
     **/
    protected AppLog getOperationLog(ApplicationLog applicationLog, JoinPoint joinPoint) {
        return getOperationLog(joinPoint, applicationLog.value(), applicationLog.key());
    }

    /**
     * 获取封装 {@link AppLog} 的实例
     *
     * @param joinPoint    切入点
     * @param businessName 业务操作名称
     * @param key          业务操作的唯一标识
     * @return AppLog
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:15
     **/
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
        // 参数名 ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        AppLog.AppLogBuilder opt = AppLog.builder();
        String paramsValue = StringUtils.getParamsValue(params);
        String headers = StringUtils.getHeader(request);

        opt.clazz(className).methodName(methodName).logName(businessName).type(key).parameters(paramsValue)
                .ipAddress(IpUtils.getIpAddr(request)).url(URLUtil.getPath(request.getRequestURI()))
                .httpType(request.getMethod()).userAgent(request.getHeader(Constants.USER_TYPE))
                .beginTime(beginTime).headers(headers);

        return opt.build();
    }


    /**
     * 获取 {@link ApplicationLog} 注解
     *
     * @param joinPoint 切点
     * @return ApplicationLog
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/24-16:22
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

}
