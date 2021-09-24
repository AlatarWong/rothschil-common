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
import xyz.wongs.drunkard.base.aop.annotion.ApplicationLog;
import xyz.wongs.drunkard.base.aop.pojo.OperationLog;
import xyz.wongs.drunkard.base.handler.impl.QueueTaskHandler;
import xyz.wongs.drunkard.base.queue.AppLogQueue;
import xyz.wongs.drunkard.base.utils.DateUtils;
import xyz.wongs.drunkard.base.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

/** AOP处理基类，定义队列和方法
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 16:31
 * @version 1.0.0
 */
public abstract class AbsAspect {

    @Autowired
    protected QueueTaskHandler queueTaskHandler;

    @Autowired
    protected AppLogQueue appLogQueue;

    protected void doFinal(ThreadLocal<OperationLog> threadLocal,Object ret, Exception e){
        int success = 0;
        Date endTime = Date.from(Instant.now());
        OperationLog operationLog = threadLocal.get();
        if(null!=e){
            success=-1;
            operationLog.setErrMsg(e.getMessage());
        }
        if(null!=ret){
            operationLog.setRespContent(JSON.toJSONString(ret));
        }
        operationLog.setEndTime(endTime);
        operationLog.setIsSuccess(success);
        operationLog.setCost(DateUtils.getMills(operationLog.getBeginTime(),endTime));
        threadLocal.remove();
        queueTaskHandler.setOperationLog(operationLog);
        appLogQueue.addQueue(queueTaskHandler);
    }


    protected OperationLog getOperationLog(ApplicationLog applicationLog, JoinPoint joinPoint){
        return getOperationLog(joinPoint,applicationLog.value(),applicationLog.key());
    }


    protected OperationLog getOperationLog(JoinPoint joinPoint,String businessName,String key){
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
        OperationLog.OperationLogBuilder opt = OperationLog.builder();

        opt.className(className).methodName(methodName).logName(businessName).logType(key)
                .ipAddress(IpUtils.getIpAddr(request)).actionUrl(URLUtil.getPath(request.getRequestURI()))
                .requestMethod(request.getMethod()).userAgent(request.getHeader("user-agent"))
                .beginTime(beginTime).reqContent(JSON.toJSONString(params));
        return opt.build();
    }

    /** 获取 ApplicationLog 注解
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/24-16:22
     * @param joinPoint
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
