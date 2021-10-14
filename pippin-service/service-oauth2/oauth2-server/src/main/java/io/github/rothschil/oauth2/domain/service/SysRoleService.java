package io.github.rothschil.oauth2.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.oauth2.domain.entity.SysRole;
import io.github.rothschil.oauth2.domain.mapper.SysRoleMapper;


@Service
@Transactional(readOnly = true)
public class SysRoleService extends BaseService<SysRole, Long> {


	private SysRoleMapper sysRoleMapper;

	@Autowired
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	@Override
	protected BaseMapper<SysRole, Long> getMapper() {
		return sysRoleMapper;
	}

}
