package xyz.wongs.drunkard.base.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import xyz.wongs.drunkard.base.response.enums.Status;
import xyz.wongs.drunkard.common.exception.DrunkardException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** 获取 {@link Properties} 的属性内容，首次使用，将所有属性内容 加载到内存中的 {@link Map} 中，后续再使用，直接从内存中获取
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/9/24 - 21:42
 * @since 1.0.0
 */
public class PropConfig {

    private static final Logger LOG = LoggerFactory.getLogger(PropConfig.class);

    public static Map<String, String> propertiesMap;

    /** 加载并且处理 Properties 文件
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-11:37
     * @param props Properties 文件
     **/
    private static void processProperties(Properties props) {
        propertiesMap = new HashMap<>(32);
        for (Object o : props.keySet()) {
            String key = o.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                String value = new String(props.getProperty(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                LOG.debug("[key] {} \t [value] {}", key, value);
                propertiesMap.put(key, value);
            } catch (Exception e) {
                throw new DrunkardException();
            }
        }
    }

    public static void loadAllProperties(String propertyFileName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
            processProperties(properties);
        } catch (DrunkardException e) {
            throw new DrunkardException(Status.INIT_FAIL_PROPERTIE,propertyFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return propertiesMap.get(name);
    }

    @SuppressWarnings("unused")
    public static Map<String, String> getAllProperty() {
        return propertiesMap;
    }
}
