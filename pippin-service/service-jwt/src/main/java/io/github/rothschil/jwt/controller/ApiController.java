package io.github.rothschil.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.rothschil.jwt.annotation.IgnoreTokenCheck;
import io.github.rothschil.jwt.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.github.rothschil.base.response.annoation.Body;
import io.github.rothschil.jwt.annotation.LoginToken;
import io.github.rothschil.service.JwtService;
import io.github.rothschil.service.UserService;

import java.util.ArrayList;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/7 - 19:19
 * @since 1.0.0
 */
@RestController
@RequestMapping("api")
public class ApiController {

    private UserService userService;

    private JwtService jwtService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Body
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        User userForBase = userService.findByUsername(user);
        if (userForBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
        } else {
            if (!userForBase.getName().equals(user.getName())) {
                jsonObject.put("message", "登录失败,密码错误");
            } else {
                String token = jwtService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
            }
        }
        return jsonObject;
    }


    @Body
    @LoginToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证";
    }

    @Body
    @RequestMapping(value = "/users")
    @IgnoreTokenCheck
    public ArrayList<String> usersList() {
        return new ArrayList<String>() {{
            add("freewolf");
            add("tom");
            add("jerry");
        }};
    }
}
