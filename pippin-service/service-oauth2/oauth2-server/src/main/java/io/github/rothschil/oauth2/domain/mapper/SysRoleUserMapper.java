package io.github.rothschil.oauth2.domain.mapper;


import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.oauth2.domain.entity.SysRoleUser;

public interface SysRoleUserMapper extends BaseMapper<SysRoleUser,Long> {
    @Override
    int deleteByPrimaryKey(Long id);

    @Override
    SysRoleUser selectByPrimaryKey(Long id);

    @Override
    int updateByPrimaryKeySelective(SysRoleUser record);

    @Override
    int updateByPrimaryKey(SysRoleUser record);
}