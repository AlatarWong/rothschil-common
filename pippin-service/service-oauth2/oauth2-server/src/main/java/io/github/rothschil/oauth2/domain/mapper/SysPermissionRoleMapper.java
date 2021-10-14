package io.github.rothschil.oauth2.domain.mapper;


import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.oauth2.domain.entity.SysPermissionRole;

public interface SysPermissionRoleMapper extends BaseMapper<SysPermissionRole,Long> {
    @Override
    int deleteByPrimaryKey(Long id);

    @Override
    SysPermissionRole selectByPrimaryKey(Long id);

    @Override
    int updateByPrimaryKeySelective(SysPermissionRole record);

    @Override
    int updateByPrimaryKey(SysPermissionRole record);
}