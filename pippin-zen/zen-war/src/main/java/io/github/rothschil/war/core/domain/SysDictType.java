package io.github.rothschil.war.core.domain;

import io.github.rothschil.common.annotation.Excel;
import io.github.rothschil.common.annotation.Excel.ColumnType;
import io.github.rothschil.common.po.BasePo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典类型表
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 23:53
 * @since 1.0.0
 */
public class SysDictType extends BasePo<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long id;

    /**
     * 字典名称
     */
    @Excel(name = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictId", getId())
                .append("dictName", getDictName())
                .append("dictType", getDictType())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
