package xyz.wongs.drunkard.common.utils.enums;

import xyz.wongs.drunkard.common.constant.Constants;

/** 定义操作系统的管理员角色
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/12 - 14:36
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum OsEnum {

    /**
     * Unix
     */
    UNIX("unix", Constants.ADMIN_UNIX),

    /**
     * linux
     */
    LINUX("linux", Constants.ADMIN_UNIX),

    /**
     * windows
     */
    WIN("windows", Constants.ADMIN_WIN_ADMIN);

    /**
     *
     */
    private String osType;

    /**
     *
     */
    private String admin;

    OsEnum(String osType, String admin) {
        this.osType = osType;
        this.admin = admin;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
