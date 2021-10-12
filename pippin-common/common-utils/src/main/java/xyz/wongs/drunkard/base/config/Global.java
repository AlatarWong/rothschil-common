package xyz.wongs.drunkard.base.config;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.common.exception.DrunkardException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;


/**
 * 全局配置类 ，让应用在第一次调用过程完成，属性值的实例化，与 {@link xyz.wongs.drunkard.base.property.PropConfig} 有功能类似，可以留一个
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/26 - 17:08
 * @since 1.0.0
 */
@Component
public class Global {

    private static final Logger LOG = LoggerFactory.getLogger(Global.class);
    /**
     * 保存全局属性值
     */
    private static final Map<String, String> GLOBAL_ATTR_MAP = Maps.newHashMap();

    /**
     * 根据Key 获取配置值
     *
     * @param key 键
     * @return String Value
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-17:08
     **/
    public static String getConfig(String key) {
        String value = GLOBAL_ATTR_MAP.get(key);
        if (null == value) {
            init();
        }
        return GLOBAL_ATTR_MAP.get(key);
    }

    private synchronized static void init() {
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties("config.properties");
            for (Object o : props.keySet()) {
                String key = o.toString();
                try {
                    // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                    String value = new String(props.getProperty(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    LOG.error("[key] {} \t [value] {}", key, value);
                    GLOBAL_ATTR_MAP.put(key, value);
                } catch (DrunkardException e) {
                    LOG.error("[key]={}, [errMsg]={}", key, e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
