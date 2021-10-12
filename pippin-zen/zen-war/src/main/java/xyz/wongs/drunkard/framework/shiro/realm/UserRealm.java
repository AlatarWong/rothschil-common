package xyz.wongs.drunkard.framework.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wongs.drunkard.common.utils.ShiroUtils;
import xyz.wongs.drunkard.common.utils.StringUtils;
import xyz.wongs.drunkard.framework.shiro.service.SysLoginService;
import xyz.wongs.drunkard.war.core.domain.SysUser;
import xyz.wongs.drunkard.war.core.service.ISysMenuService;
import xyz.wongs.drunkard.war.core.service.ISysRoleService;

import java.util.Objects;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 21:34
 * @since 1.0.0
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysLoginService loginService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SysUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roles;
        // 功能列表
        Set<String> menus;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            roles = roleService.selectRoleKeys(user.getId());
            menus = menuService.selectPermsByUserId(user.getId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (StringUtils.isNotNull(upToken.getPassword())) {
            password = new String(upToken.getPassword());
        }

        SysUser user;
        try {
            user = loginService.login(username, password);
        } catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清理指定用户授权信息缓存
     */
    @SuppressWarnings("unused")
    public void clearCachedAuthorizationInfo(Object principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        this.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清理所有用户授权信息缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
