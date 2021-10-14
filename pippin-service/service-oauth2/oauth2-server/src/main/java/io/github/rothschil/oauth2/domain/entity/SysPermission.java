package io.github.rothschil.oauth2.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.github.rothschil.common.po.BasePo;

@EqualsAndHashCode(callSuper=false)
@Data
public class SysPermission extends BasePo<Long> {
    private Long id;

    private String permName;

    private String descritpion;

    private String url;

    private Integer pid;

}