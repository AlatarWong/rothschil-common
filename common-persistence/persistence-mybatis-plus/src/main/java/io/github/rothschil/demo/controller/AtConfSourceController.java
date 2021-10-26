package io.github.rothschil.demo.controller;


import io.github.rothschil.demo.entity.AtConfSource;
import io.github.rothschil.demo.service.AtConfSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author Bean
 * @since 2021-10-23
 */
@Slf4j
@RestController
@RequestMapping("/at")
public class AtConfSourceController {

    @Autowired
    private AtConfSourceService atConfSourceService;


    @RequestMapping("/")
    public void getAll() {
        List<AtConfSource> lists = atConfSourceService.getAll();
        for (AtConfSource list : lists) {
            log.warn(list.toString());
        }
    }
}

