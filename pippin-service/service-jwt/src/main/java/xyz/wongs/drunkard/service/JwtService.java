package xyz.wongs.drunkard.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.jwt.po.User;

/**
 * @Author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2021/7/6 - 10:24
 * @since 1.0.0
 */
@Component
public class JwtService {

    /**
     * @return String
     * @Author <a href="https://github.com/rothschil">Sam</a>
     * @Description //TODO
     * @Date 2021/7/6-10:24
     * @Param user
     **/
    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getName()));
        return token;
    }
}
