package io.github.rothschil.web.compoent;

import io.github.rothschil.domain.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestCompoent {


//    @Cacheable(cacheNames = {"user","user"},key = "userVo:account")
    @Cacheable(cacheNames = {"user","user"},key = "#userVo.account")
    public UserVo get(UserVo userVo) {
        // 模拟耗时操作
        try {
            Thread.sleep(1000);
            log.info(userVo.getAccount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return UserVo.builder().email("wongs@qq.com").account("张三").password("取你狗命").id(2L).build();
    }
}
