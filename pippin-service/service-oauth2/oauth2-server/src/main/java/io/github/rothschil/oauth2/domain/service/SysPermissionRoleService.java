package io.github.rothschil.oauth2.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.oauth2.domain.entity.SysPermissionRole;
import io.github.rothschil.oauth2.domain.mapper.SysPermissionRoleMapper;


@Service
@Transactional(readOnly = true)
public class SysPermissionRoleService extends BaseService<SysPermissionRole, Long> {

	@Autowired
	private SysPermissionRoleMapper sysPermissionRoleMapper;

	@Override
	protected BaseMapper<SysPermissionRole, Long> getMapper() {
		return sysPermissionRoleMapper;
	}

}
