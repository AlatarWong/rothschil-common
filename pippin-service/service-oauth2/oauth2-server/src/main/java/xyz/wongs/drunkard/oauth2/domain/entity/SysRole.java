package xyz.wongs.drunkard.oauth2.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.wongs.drunkard.common.po.BasePo;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 16:56
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class SysRole extends BasePo<Long> {

    private Long id;

    private String roleName;

}