package io.github.rothschil.domain.vo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class UserVo {

    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotNull(message = "哪一年")
    @Email(message = "哪一年")
    private int whichYear;

    @NotNull(message = "哪一月")
    @Email(message = "哪一月")
    private int whichMonth;
}
