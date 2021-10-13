package xyz.wongs.drunkard.oauth2.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.drunkard.base.persistence.mybatis.mapper.BaseMapper;
import xyz.wongs.drunkard.base.persistence.mybatis.service.BaseService;
import xyz.wongs.drunkard.oauth2.domain.entity.SysRoleUser;
import xyz.wongs.drunkard.oauth2.domain.mapper.SysRoleUserMapper;


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
