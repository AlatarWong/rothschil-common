package xyz.wongs.drunkard.design.proxy.dynamic;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 17:38
 * @since 1.0.0
 */
@Slf4j
public class CglibProxy implements MethodInterceptor {

    public CglibProxy() {
    }

    /**  通过设置 Enhancer 类来设置代理类，
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-18:03
     * @param obj
     * @return Object
     **/
    public Object getInstall(Object obj) {
        return Enhancer.create(obj.getClass(), this);
    }

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-18:01
     * @param obj 表示要进行增强的对象
     * @param method    表示拦截的方法
     * @param args  表示参数列表
     * @param proxy 表示对方法的代理
     * @return Object
     **/
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.error("Begin 执行方法前的操作");
        // 对被代理对象方法的调用
        proxy.invokeSuper(obj, args);
        log.error("End 执行方法后的操作");
        return obj;
    }
}
