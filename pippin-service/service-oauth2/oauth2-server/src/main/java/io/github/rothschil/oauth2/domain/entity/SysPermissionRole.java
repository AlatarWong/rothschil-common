package io.github.rothschil.oauth2.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.github.rothschil.common.po.BasePo;

@EqualsAndHashCode(callSuper=false)
@Data
public class SysPermissionRole extends BasePo<Long> {
    private Long id;

    private Long roleId;

    private Long permissionId;
}