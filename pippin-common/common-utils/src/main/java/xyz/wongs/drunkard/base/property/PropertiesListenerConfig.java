package xyz.wongs.drunkard.base.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.message.exception.DrunkardException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 21:42
 * @version 1.0.0
 */
public class PropertiesListenerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesListenerConfig.class);

    public static Map<String, String> propertiesMap;

    private static void processProperties(Properties props) throws BeansException {
        propertiesMap = new HashMap<String, String>();
        for (Object o : props.keySet()) {
            String key = o.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                String value = new String(props.getProperty(key).getBytes("ISO-8859-1"), Constants.UTF8);
                LOG.info("[key] {} \t [value] {}",key,value);
                propertiesMap.put(key, value);
            } catch (UnsupportedEncodingException | DrunkardException e) {
                LOG.error("[key]={}, [errMsg]={}",key,e.getMessage());
            }
        }
    }

    public static void loadAllProperties(String propertyFileName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
            processProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return propertiesMap.get(name).toString();
    }

    public static Map<String, String> getAllProperty() {
        return propertiesMap;
    }
}
