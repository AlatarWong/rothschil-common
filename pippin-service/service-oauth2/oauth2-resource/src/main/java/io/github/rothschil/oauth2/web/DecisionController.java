package io.github.rothschil.oauth2.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:32
 * @since 1.0.0
 */
@RestController
@RequestMapping("/decision")
public class DecisionController {

    @RequestMapping("/userinfo")
    public String hello(){
        return "userinfo" ;
    }
}
