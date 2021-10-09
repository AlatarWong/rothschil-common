package xyz.wongs.drunkard.common.enums;

/** 用户状态
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:04
 * @since 1.0.0
 */
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
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
