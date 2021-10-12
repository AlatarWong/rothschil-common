package xyz.wongs.drunkard.design.proxy;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 17:32
 * @since 1.0.0
 */
@Slf4j
public class Accessible implements Access{

    @Override
    public void login() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            log.info("登录成功");
        } catch (InterruptedException e) {
            log.error("登录失败 msg={}",e.getMessage());
        }

    }
}
