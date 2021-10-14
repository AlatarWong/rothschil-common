package io.github.rothschil.oauth2.domain.mapper;


import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.oauth2.domain.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission,Long> {

    @Override
    int deleteByPrimaryKey(Long id);

    @Override
    SysPermission selectByPrimaryKey(Long id);

    @Override
    int updateByPrimaryKeySelective(SysPermission record);

    @Override
    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findByAdminUserId(Long uId);
}