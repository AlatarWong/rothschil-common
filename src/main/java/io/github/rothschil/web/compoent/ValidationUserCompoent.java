package io.github.rothschil.web.compoent;

import io.github.rothschil.domain.vo.UserVo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ValidationUserCompoent {


    private static final String KEY_PREFIX = "zoe:asset:";

    @Cacheable(cacheNames = "TIMEOUT_60S", cacheManager = "synergyCacheManager", key = "'zoe:asset:' + #vo.getWhichYear()+ #vo.getWhichMonth()")
    public UserVo queryWarningResult(UserVo vo) {

        return null;
    }
}
