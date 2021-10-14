package io.github.rothschil.oauth2.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.oauth2.domain.entity.OauthUser;
import io.github.rothschil.oauth2.domain.mapper.OauthUserMapper;

/**
 * @author WCNGS@QQ.COM
 * @date 20/12/2 16:58
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class OauthUserService extends BaseService<OauthUser, Long> {

    private OauthUserMapper oauthUserMapper;

    @Autowired
    public void setOauthUserMapper(OauthUserMapper oauthUserMapper) {
        this.oauthUserMapper = oauthUserMapper;
    }

    @Override
    protected BaseMapper<OauthUser, Long> getMapper() {
        return oauthUserMapper;
    }

}
