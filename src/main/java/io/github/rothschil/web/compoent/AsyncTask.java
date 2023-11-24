package io.github.rothschil.web.compoent;

import cn.hutool.json.JSONUtil;
import io.github.rothschil.common.base.vo.RequestHeaderVo;
import io.github.rothschil.common.utils.UserTransmittableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AsyncTask {

    @Async
    public void async(){
        RequestHeaderVo vo = (RequestHeaderVo) UserTransmittableUtils.get();
        log.info("&& {}", JSONUtil.parse(vo));
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String tid = UUID.randomUUID().toString().replace("-", "");
        log.info("tid={}",tid);
    }
}
