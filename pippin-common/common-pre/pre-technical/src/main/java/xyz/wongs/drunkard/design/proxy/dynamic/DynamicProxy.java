package xyz.wongs.drunkard.design.proxy.dynamic;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.drunkard.design.proxy.Access;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 17:38
 * @since 1.0.0
 */
@Slf4j
public class DynamicProxy implements InvocationHandler {

    private Access access;

    public DynamicProxy() {
    }

    public Object getInstall(Access access) {
        this.access = access;
        return Proxy.newProxyInstance(access.getClass().getClassLoader(), access.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("代理前");
        if (method.getName().equalsIgnoreCase("login")) {
            access.login();
        }
        log.info("代理后");
        return null;
    }
}
