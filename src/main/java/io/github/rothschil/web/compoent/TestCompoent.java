package io.github.rothschil.web.compoent;

import io.github.rothschil.domain.vo.UserVo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class TestCompoent {



    @Cacheable(value = "get")
    public UserVo get() {
        // 模拟耗时操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return UserVo.builder().email("wongs@qq.com").id(2L).build();
    }
}
