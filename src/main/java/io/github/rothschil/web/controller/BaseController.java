package io.github.rothschil.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

    @RequestMapping("/")
    public String index(){
        return "redirect:/swagger-ui.html";
    }
}
