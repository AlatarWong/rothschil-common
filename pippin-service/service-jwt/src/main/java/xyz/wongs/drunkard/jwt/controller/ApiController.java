package xyz.wongs.drunkard.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wongs.drunkard.base.response.annoation.Body;
import xyz.wongs.drunkard.jwt.annotation.IgnoreTokenCheck;
import xyz.wongs.drunkard.jwt.annotation.LoginToken;
import xyz.wongs.drunkard.jwt.po.User;
import xyz.wongs.drunkard.service.JwtService;
import xyz.wongs.drunkard.service.UserService;

import java.util.ArrayList;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2020/11/7 - 19:19
 * @since 1.0.0
 */
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Body
    @PostMapping("/login")
    public Object login(@RequestBody User user){
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findByUsername(user);
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
        }else {
            if(!userForBase.getName().equals(user.getName())){
                jsonObject.put("message","登录失败,密码错误");
            }else {
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
    public String getMessage(){
        return "你已通过验证";
    }

    @Body
    @RequestMapping(value = "/users")
    @IgnoreTokenCheck
    public ArrayList usersList() {
        ArrayList<String> users =  new ArrayList<String>(){{
            add("freewolf");
            add("tom");
            add("jerry");
        }};
        return users;
    }
}
