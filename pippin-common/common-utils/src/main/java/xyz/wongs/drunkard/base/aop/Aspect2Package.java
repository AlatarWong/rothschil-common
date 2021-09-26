package xyz.wongs.drunkard.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.aop.pojo.OperationLog;

/**
 * 应用全局日志APO 异步日志：
 * 1、正常下执行次序是：@Around @Before ${METHOD} @Around @After @AfterReturning；
 * 2、异常下执行次序是：@Around @Before ${METHOD} @After @AfterThrowing;
 * 处理 全局Controller下面的public 方法
 * @author WCNGS@QQ.COM
 * 
 * @date 20/12/2 10:23
 * @Version 1.0.0
 */
@Order(2)
@Aspect
@Component
public class Aspect2Package extends AbsAspect {

    private static final Logger LOG = LoggerFactory.getLogger(Aspect2Package.class);

    protected final ThreadLocal<OperationLog> threadLocal = new ThreadLocal<>();

    /** 切面
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/24-16:39
     **/
    @Pointcut("execution(public * xyz.wongs..*.web.*Controller.*(..))")
    public void cutService() {

    }

    @Before(value = "cutService()")
    public void before(JoinPoint joinPoint) {
        OperationLog operationLog = getOperationLog(joinPoint,null,null);
        threadLocal.set(operationLog);
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
