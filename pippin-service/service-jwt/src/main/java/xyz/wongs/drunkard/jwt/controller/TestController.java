package xyz.wongs.drunkard.jwt.controller;

import org.springframework.web.bind.annotation.*;
import xyz.wongs.drunkard.base.message.annoation.Body;
import xyz.wongs.drunkard.jwt.po.User;

/**
 * @Author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2021/7/7 - 19:19
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
