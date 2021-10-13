package xyz.wongs.drunkard.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.aop.annotation.ApplicationLog;
import xyz.wongs.drunkard.base.aop.pojo.AppLog;

/**
 * 应用全局日志AOP， 预计注解，并且对拦截信息进行生成基本信息，异步处理
 * 1、正常下执行次序是：@Around @Before ${METHOD} @Around @After @AfterReturning；
 * 2、异常下执行次序是：@Around @Before ${METHOD} @After @AfterThrowing;
 * 之处理 在方法上有注解
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/2 10:23
 * @since 1.0.0
 */
@Order(1)
@Aspect
@Component
public class Aspect2Annotaion extends AbsAspect {

    private final ThreadLocal<AppLog> threadLocal = new ThreadLocal<>();

    /**
     * 定义需要拦截的切面，这里是基于注解进行拦截
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/24-16:39
     **/
    @Pointcut(value = "@annotation(xyz.wongs.drunkard.base.aop.annotation.ApplicationLog)")
    public void cutService() {
    }

    @Before(value = "cutService()")
    public void before(JoinPoint joinPoint) {
        ApplicationLog applicationLog = getApplicationLog(joinPoint);
        if (null == applicationLog) {
            return;
        }
        AppLog appLog = getOperationLog(applicationLog, joinPoint);
        threadLocal.remove();
        threadLocal.set(appLog);
    }

    @AfterReturning(returning = "ret", pointcut = "cutService()")
    public void afterReturning(Object ret) {
        send2Queue(threadLocal, ret, null);
    }

    @AfterThrowing(value = "cutService()", throwing = "e")
    public void afterThrowing(Exception e) {
        send2Queue(threadLocal, null, e);
    }

}
