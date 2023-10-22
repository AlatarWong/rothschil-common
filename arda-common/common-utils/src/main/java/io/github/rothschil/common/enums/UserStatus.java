package io.github.rothschil.common.enums;

/**
 * 用户状态
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 21:04
 * @since 1.0.0
 */
public enum UserStatus {

    /**
     * 正常状态
     */
    OK("0", "正常"),

    /**
     * 停用状态
     */
    DISABLE("1", "停用"),

    /**
     * 删除状态
     */
    DELETED("2", "删除");

    /**
     * 操作编码
     */
    private final String code;

    /**
     * 编码内容
     */
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
