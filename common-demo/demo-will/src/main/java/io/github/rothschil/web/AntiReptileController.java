package io.github.rothschil.web;

import cn.keking.anti_reptile.annotation.AntiReptile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class AntiReptileController {

    @AntiReptile
    @GetMapping("")
    public String demo(){
        return "demo";
    }
}
