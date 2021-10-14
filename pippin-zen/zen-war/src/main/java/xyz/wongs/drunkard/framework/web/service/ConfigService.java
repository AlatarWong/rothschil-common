package xyz.wongs.drunkard.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.rothschil.war.core.service.ISysConfigService;

/**
 * html调用 thymeleaf 实现参数管理
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 20:51
 * @since 1.0.0
 */
@Service("config")
public class ConfigService {
    @Autowired
    private ISysConfigService configService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String getKey(String configKey) {
        return configService.selectConfigByKey(configKey);
    }
}
