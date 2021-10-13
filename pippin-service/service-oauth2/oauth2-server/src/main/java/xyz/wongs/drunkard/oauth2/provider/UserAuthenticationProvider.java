package xyz.wongs.drunkard.oauth2.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 用户自定义身份认证
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:33
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Component
public class UserAuthenticationProvider extends AbsProvider implements AuthenticationProvider {

    /**
     * 认证处理，返回一个Authentication的实现类则代表认证成功，返回null则代表认证失败
     *
     * @date 2019/7/5 15:19
     * @since 1.0
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authentication(authentication,false);
    }

    /**
     * @Description 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @date 2019/7/5 15:18
     * @since 1.0
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}
