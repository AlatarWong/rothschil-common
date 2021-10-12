package xyz.wongs.drunkard.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.jwt.po.User;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2020/11/6 - 10:24
 * @since 1.0.0
 */
@Component
public class JwtService {

    /**
     * @return String
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @Description //TODO
     * @date 2020/11/6-10:24
     * @param user
     **/
    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getName()));
        return token;
    }
}
