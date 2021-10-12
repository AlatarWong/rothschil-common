package xyz.wongs.drunkard.war.web.controller.monitor;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.wongs.drunkard.common.core.controller.BaseController;
import xyz.wongs.drunkard.common.core.domain.AjaxResult;
import xyz.wongs.drunkard.common.core.page.TableDataInfo;
import xyz.wongs.drunkard.common.enums.OnlineStatus;
import xyz.wongs.drunkard.common.text.Convert;
import xyz.wongs.drunkard.common.utils.ShiroUtils;
import xyz.wongs.drunkard.framework.shiro.session.OnlineSession;
import xyz.wongs.drunkard.framework.shiro.session.OnlineSessionDAO;
import xyz.wongs.drunkard.war.core.domain.SysUserOnline;
import xyz.wongs.drunkard.war.core.service.ISysUserOnlineService;

import java.util.List;


/**
 * 在线用户监控
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 20:57
 * @since 1.0.0
 */
@Controller
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {

    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    @RequiresPermissions("monitor:online:view")
    @GetMapping()
    public String online() {
        String prefix = "monitor/online";
        return prefix + "/online";
    }

    @RequiresPermissions("monitor:online:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUserOnline userOnline) {
        startPage();
        List<SysUserOnline> list = userOnlineService.selectUserOnlineList(userOnline);
        return getDataTable(list);
    }

    @RequiresPermissions(value = {"monitor:online:batchForceLogout", "monitor:online:forceLogout"}, logical = Logical.OR)
    @PostMapping("/batchForceLogout")
    @ResponseBody
    public AjaxResult batchForceLogout(String ids) {
        for (String sessionId : Convert.toStrArray(ids)) {
            SysUserOnline online = userOnlineService.selectOnlineById(sessionId);
            if (online == null) {
                return error("用户已下线");
            }
            OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
            if (onlineSession == null) {
                return error("用户已下线");
            }
            if (sessionId.equals(ShiroUtils.getSessionId())) {
                return error("当前登陆用户无法强退");
            }
            onlineSession.setStatus(OnlineStatus.OFF_LINE);
            onlineSessionDAO.update(onlineSession);
            online.setStatus(OnlineStatus.OFF_LINE);
            userOnlineService.saveOnline(online);
        }
        return success();
    }
}
