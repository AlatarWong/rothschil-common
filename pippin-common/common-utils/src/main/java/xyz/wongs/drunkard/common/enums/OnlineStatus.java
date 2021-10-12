package xyz.wongs.drunkard.common.enums;

/**
 * 用户会话
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 20:58
 * @since 1.0.0
 */
public enum OnlineStatus {
    /**
     * 用户状态
     */
    ON_LINE("在线"), OFF_LINE("离线");

    private final String info;

    OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}