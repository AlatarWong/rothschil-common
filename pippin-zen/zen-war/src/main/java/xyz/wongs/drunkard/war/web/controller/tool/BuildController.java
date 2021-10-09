package xyz.wongs.drunkard.war.web.controller.tool;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.wongs.drunkard.common.core.controller.BaseController;

/** build 表单构建
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:10
 * @since 1.0.0
 */
@Controller
@RequestMapping("/tool/build")
public class BuildController extends BaseController
{
    private String prefix = "tool/build";

    @RequiresPermissions("tool:build:view")
    @GetMapping()
    public String build()
    {
        return prefix + "/build";
    }
}
