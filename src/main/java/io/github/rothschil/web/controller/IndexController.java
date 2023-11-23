package io.github.rothschil.web.controller;

import cn.hutool.json.JSONUtil;
import io.github.rothschil.common.base.vo.RequestHeaderVo;
import io.github.rothschil.common.utils.UserTransmittableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequestMapping("/iserv")
@RestController
public class IndexController {

    @Value("${github.active:test}")
    protected String active;

    @RequestMapping("/index")
    public String index(){
        RequestHeaderVo vo = (RequestHeaderVo)UserTransmittableUtils.get();
        log.info("&& {}",JSONUtil.parse(vo));
        return active+":->"+UUID.randomUUID().toString();
    }
}
