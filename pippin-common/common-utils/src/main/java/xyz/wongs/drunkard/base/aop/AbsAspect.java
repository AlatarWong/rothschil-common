package xyz.wongs.drunkard.base.aop;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.wongs.drunkard.base.aop.annotation.ApplicationLog;
import xyz.wongs.drunkard.base.aop.pojo.AppLog;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.handler.impl.QueueTaskHandler;
import xyz.wongs.drunkard.base.queue.AppLogQueue;
import xyz.wongs.drunkard.base.utils.DateUtils;
import xyz.wongs.drunkard.base.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

/** 定义AOP处理通用方法，引入 {@link xyz.wongs.drunkard.base.queue.AppLogQueue} 异步队列模块 和 {@link xyz.wongs.drunkard.base.handler.impl.QueueTaskHandler}
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/24 - 16:31
 * @since 1.0.0
 */
public abstract class AbsAspect {

    @Autowired
    protected QueueTaskHandler queueTaskHandler;

    @Autowired
    protected AppLogQueue appLogQueue;

    protected void send2Queue(ThreadLocal<AppLog> threadLocal, Object ret, Exception e){
        int success = 0;
        Date endTime = Date.from(Instant.now());
        AppLog appLog = threadLocal.get();
        if(null!=e){
            success=-1;
            appLog.setErr(e.getMessage());
        }
        if(null!=ret){
            appLog.setRespContent(JSON.toJSONString(ret));
        }
        appLog.setEndTime(endTime);
        appLog.setSucceed(success);
        appLog.setCost(DateUtils.getMills(appLog.getBeginTime(),endTime));
        threadLocal.remove();
        queueTaskHandler.setOperationLog(appLog);
        appLogQueue.addQueue(queueTaskHandler);
    }


    protected AppLog getOperationLog(ApplicationLog applicationLog, JoinPoint joinPoint){
        return getOperationLog(joinPoint,applicationLog.value(),applicationLog.key());
    }


    protected AppLog getOperationLog(JoinPoint joinPoint, String businessName, String key){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        Assert.notNull(sra,"The ServletRequestAttributes must not be null");
        HttpServletRequest request = sra.getRequest();
        //获取拦截的方法名
        Date beginTime = Date.from(Instant.now());
        String methodName = joinPoint.getSignature().getName();
        // 类名
        String className =joinPoint.getTarget().getClass().getName();
        // 参数
        Object[] params = joinPoint.getArgs();
        AppLog.AppLogBuilder opt = AppLog.builder();

        opt.clazz(className).methodName(methodName).logName(businessName).type(key)
                .ipAddress(IpUtils.getIpAddr(request)).url(URLUtil.getPath(request.getRequestURI()))
                .httpType(request.getMethod()).userAgent(request.getHeader(Constants.USER_TYPE))
                .beginTime(beginTime).reqContent(JSON.toJSONString(params));
        return opt.build();
    }

    /** 获取 ApplicationLog 注解
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/24-16:22
     * @param joinPoint 切点
     * @return ApplicationLog
     **/
    protected static ApplicationLog getApplicationLog(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null){
            return method.getAnnotation(ApplicationLog.class);
        }
        return null;
    }
}
