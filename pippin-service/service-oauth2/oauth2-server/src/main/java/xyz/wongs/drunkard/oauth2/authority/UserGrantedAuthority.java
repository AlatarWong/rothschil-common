package xyz.wongs.drunkard.oauth2.authority;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;

import java.util.Map;

/**
 * 自定义GrantedAuthority接口
 *
 * @author WCNGS@QQ.COM
 * @date 2019/7/29 16:14
 * @since 1.0
 */
public class UserGrantedAuthority implements GrantedAuthority {
    private final Map<String, Object> author = Maps.newHashMap();

    public UserGrantedAuthority(String name, Object value) {
        author.put(name, value);
    }

    @Override
    public String getAuthority() {
        return JSONUtils.toJSONString(author);
    }
}
