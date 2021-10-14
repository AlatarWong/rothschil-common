package io.github.rothschil.oauth2.domain.service;


import io.github.rothschil.oauth2.domain.entity.SysRoleUser;
import io.github.rothschil.oauth2.domain.mapper.SysRoleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;


/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:56
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class SysRoleUserService extends BaseService<SysRoleUser, Long> {

	private SysRoleUserMapper sysRoleUserMapper;

	@Autowired
	public void setSysRoleUserMapper(SysRoleUserMapper sysRoleUserMapper) {
		this.sysRoleUserMapper = sysRoleUserMapper;
	}

	@Override
	protected BaseMapper<SysRoleUser, Long> getMapper() {
		return sysRoleUserMapper;
	}

}
