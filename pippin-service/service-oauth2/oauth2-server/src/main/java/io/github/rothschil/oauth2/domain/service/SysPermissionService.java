package io.github.rothschil.oauth2.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.oauth2.domain.entity.SysPermission;
import io.github.rothschil.oauth2.domain.mapper.SysPermissionMapper;

import java.util.List;


/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:49
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class SysPermissionService extends BaseService<SysPermission, Long> {

	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	public void setSysPermissionMapper(SysPermissionMapper sysPermissionMapper) {
		this.sysPermissionMapper = sysPermissionMapper;
	}

	@Override
	protected BaseMapper<SysPermission, Long> getMapper() {
		return sysPermissionMapper;
	}

	public List<SysPermission> findByAdminUserId(Long uId){
		return sysPermissionMapper.findByAdminUserId(uId);
	}
}
