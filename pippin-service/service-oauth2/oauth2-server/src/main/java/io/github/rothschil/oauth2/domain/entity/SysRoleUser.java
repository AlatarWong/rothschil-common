package io.github.rothschil.oauth2.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.github.rothschil.common.po.BasePo;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 11:54
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class SysRoleUser extends BasePo<Long> {

    private Long id;

    private Long userId;

    private Long roleId;
}