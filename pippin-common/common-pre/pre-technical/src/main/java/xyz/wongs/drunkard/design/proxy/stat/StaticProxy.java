package xyz.wongs.drunkard.design.proxy.stat;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.drunkard.design.proxy.Access;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 17:38
 * @since 1.0.0
 */
@Slf4j
public class StaticProxy implements Access {

    private Access access;

    public StaticProxy(){
    }

    public StaticProxy(Access access){
        this.access=access;
    }

    @Override
    public void login() {
        long start = System.currentTimeMillis();
        log.warn("start proxy : " + start);
        access.login();
        long end = System.currentTimeMillis();
        log.warn("end proxy : " + end);
        log.warn("spend all time : " + (end - start) + " ms.");
    }
}
