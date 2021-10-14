package io.github.rothschil.oauth2.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.exception.DrunkardException;
import io.github.rothschil.oauth2.domain.service.UserAuthDetailsService;

import java.util.Collection;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 17:05
 * @since 1.0.0
 */
public abstract class AbsProvider {

    protected UserAuthDetailsService authUserDetailsService;

    protected PasswordEncoder passwordEncoder;

    @Autowired
    public void setAuthUserDetailsService(UserAuthDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-17:11
     * @param authentication 鉴权
     * @param isSms True：验证码，False：用户密码
     * @return Authentication
     **/
    protected Authentication authentication(Authentication authentication,boolean isSms){
        String userName = authentication.getName();
        if (StringUtils.isBlank(userName)) {
            throw new UsernameNotFoundException("username用户名不可以为空");
        }
        Status status = isSms? Status.USER_SMS_ERR:Status.USER_PASSWORD_ERR;

        String credentials = (String) authentication.getCredentials();
        if (StringUtils.isBlank(credentials)) {
            throw new DrunkardException(status,"内容为空");
        }
        //获取用户信息
        UserDetails user = authUserDetailsService.loadUserByUsername(userName);

        String codeCache = "1234";

        String value = isSms? codeCache:user.getPassword();
        if (!passwordEncoder.matches(credentials, value)) {
            //发布密码不正确事件
            // publisher.publishEvent(new UserLoginFailedEvent(authentication));
            throw new DrunkardException(status);
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, credentials, authorities);
    }
}
