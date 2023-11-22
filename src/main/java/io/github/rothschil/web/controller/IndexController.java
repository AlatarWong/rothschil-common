package io.github.rothschil.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class IndexController {

    @Value("${github.active:test}")
    protected String active;

    @RequestMapping("/index")
    public String index(){
        return active+":->"+UUID.randomUUID().toString();
    }
}
