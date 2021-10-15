package io.github.rothschil.base.aop;

import io.github.rothschil.base.aop.pojo.AppLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 应用全局日志APO 异步日志：
 * <ul>
 * <li>1、正常下执行次序是：@Around @Before ${METHOD} @Around @After @AfterReturning；</li>
 * <li>2、异常下执行次序是：@Around @Before ${METHOD} @After @AfterThrowing;</li>
 * </ul>
 * 处理 全局Controller下面的public 方法
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/2 10:23
 * @since 1.0.0
 */
@Order(2)
@Aspect
@Component
public class Aspect2Package extends AbsAspect {

    protected final ThreadLocal<AppLog> threadLocal = new ThreadLocal<>();

    /**
     * 切面
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/24-16:39
     **/
    @Pointcut("execution(public * xyz.wongs..*.web.*Controller.*(..))")
    public void cutService() {

    }

    /**
     * 在所有操作之前的处理逻辑
     *
     * @param joinPoint 切入点
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:18
     **/
    @Before(value = "cutService()")
    public void before(JoinPoint joinPoint) {
        AppLog appLog = getOperationLog(joinPoint, null, null);
        threadLocal.remove();
        threadLocal.set(appLog);
    }


    /**
     * 操作处理完成后，返回处理结果之前的处理逻辑
     *
     * @param ret 处理后的响应内容
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:19
     **/
    @AfterReturning(returning = "ret", pointcut = "cutService()")
    public void afterReturning(Object ret) {
        send2Queue(threadLocal, ret, null);
    }


    /**
     * 在异常发生后，处理逻辑
     *
     * @param e 异常信息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/14-22:20
     **/
    @AfterThrowing(value = "cutService()", throwing = "e")
    public void afterThrowing(Exception e) {
        send2Queue(threadLocal, null, e);
    }


}
