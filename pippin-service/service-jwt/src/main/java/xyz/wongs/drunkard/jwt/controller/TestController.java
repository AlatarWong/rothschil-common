package xyz.wongs.drunkard.jwt.controller;

import org.springframework.web.bind.annotation.*;
import xyz.wongs.drunkard.base.response.annoation.Body;
import xyz.wongs.drunkard.jwt.po.User;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2020/11/7 - 19:19
 * @since 1.0.0
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Body
    @GetMapping("/")
    public User login(){
        throw new RuntimeException();
    }

}
