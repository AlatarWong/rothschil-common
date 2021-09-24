package xyz.wongs.drunkard.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.aop.annotion.ApplicationLog;
import xyz.wongs.drunkard.base.aop.pojo.OperationLog;

/**
 * 应用全局日志APO 异步日志，正常下执行次序是：@Around @Before ${METHOD} @Around @After @AfterReturning；异常下执行次序是：@Around @Before ${METHOD} @After @AfterThrowing;
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 20/12/2 10:23
 * @Version 1.0.0
*/
@Order(1)
@Aspect
@Component
public class Aspect2Annotaion extends AbsAspect{

    private final ThreadLocal<OperationLog> threadLocal = new ThreadLocal<>();

    @Pointcut(value = "@annotation(xyz.wongs.drunkard.base.aop.annotion.ApplicationLog)")
    public void cutService() {}

    @Before(value = "cutService()")
    public void before(JoinPoint joinPoint) {
        ApplicationLog applicationLog = getApplicationLog(joinPoint);
        if(null==applicationLog){
            return ;
        }
        OperationLog operationLog = getOperationLog(applicationLog,joinPoint);
        threadLocal.set(operationLog);
    }

    @AfterReturning(returning = "ret",pointcut = "cutService()")
    public void afterReturning(Object ret){
        doFinal(threadLocal,ret,null);
    }

    @AfterThrowing(value ="cutService()", throwing = "e")
    public void afterThrowing(Exception e) {
        doFinal(threadLocal,null,e);
    }


}
