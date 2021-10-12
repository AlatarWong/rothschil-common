package xyz.wongs.drunkard.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.drunkard.base.utils.bean.BeanUtils;
import xyz.wongs.drunkard.war.core.domain.SysUser;

/**
 * shiro 工具类，主要获取 当前登录主题、登录用户、登出 等操作。
 *
 * @author WCNGS@QQ.COM
 * @date 20/12/9 14:52
 * @since 1.0.0
 */
public class ShiroUtils {

    /** 获取当前登录 {@link Subject} 主题
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:47
     * @return Subject
     **/
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /** 获取当前登录的 Session
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:48
     * @return Session
     **/
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /** 当前登录的账号登出
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:48
     **/
    public static void logout() {
        getSubject().logout();
    }

    /** 获取当前登录用户
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:45
     * @return SysUser
     **/
    public static SysUser getSysUser() {

        SysUser user = null;
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new SysUser();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:45
     * @param user 系统中的用户
     **/
    public static void setSysUser(SysUser user) {

        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    /** 生成随机盐
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/10-16:46
     * @return String
     **/
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(3).toHex();
    }

    public static Long getUserId() {
        return getSysUser().getId();
    }

    public static String getLoginName() {
        return getSysUser().getLoginName();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }


}
