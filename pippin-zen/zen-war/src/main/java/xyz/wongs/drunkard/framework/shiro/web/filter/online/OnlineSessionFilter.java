package xyz.wongs.drunkard.framework.shiro.web.filter.online;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import xyz.wongs.drunkard.common.enums.OnlineStatus;
import xyz.wongs.drunkard.common.utils.ShiroUtils;
import xyz.wongs.drunkard.framework.shiro.session.OnlineSession;
import xyz.wongs.drunkard.framework.shiro.session.OnlineSessionDAO;
import xyz.wongs.drunkard.war.constant.ShiroConstants;
import xyz.wongs.drunkard.war.core.domain.SysUser;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义访问控制
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 21:35
 * @since 1.0.0
 */
public class OnlineSessionFilter extends AccessControlFilter {
    /**
     * 强制退出后重定向的地址
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null) {
            return true;
        }
        Session session = onlineSessionDAO.readSession(subject.getSession().getId());
        if (session instanceof OnlineSession) {
            OnlineSession onlineSession = (OnlineSession) session;
            request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
            // 把user对象设置进去
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;
            if (isGuest) {
                SysUser user = ShiroUtils.getSysUser();
                if (user != null) {
                    onlineSession.setUserId(user.getId());
                    onlineSession.setLoginName(user.getLoginName());
                    onlineSession.setAvatar(user.getAvatar());
                    onlineSession.setDeptName(user.getDept().getDeptName());
                    onlineSession.markAttributeChanged();
                }
            }

            return onlineSession.getStatus() != OnlineStatus.OFF_LINE;
        }
        return true;
    }


    /** 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/12-16:47
     * @param request   ServletRequest
     * @param response  ServletResponse
     * @return boolean
     **/
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject != null) {
            subject.logout();
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    /**
     * 跳转到登录页
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/12-16:46
     **/
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO) {
        this.onlineSessionDAO = onlineSessionDAO;
    }
}
