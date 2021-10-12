package xyz.wongs.drunkard.common.utils;

import xyz.wongs.drunkard.common.enums.SystemOsProperty;

/**
 * 判断操作系统 和 当前应用的用户
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2020/10/1 - 11:32
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class OsUtil {


    /**
     * 判断当前应用启动用户是否为管理员角色，否则应该提醒用户 进一步操作，尽量规避在生产环境下误操作
     *
     * @return boolean 管理员 True，非管理员 False
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/12-14:34
     **/
    public static boolean isAdmin() {

        String os = System.getProperty(SystemOsProperty.OS_NAME.getCode());
        String ur = System.getProperty(SystemOsProperty.USER_NAME.getCode());
        // 未完待续
        return false;
    }
}
