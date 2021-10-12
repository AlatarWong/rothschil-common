package xyz.wongs.drunkard.common.enums;

/**
 * 通过 System.getProperty("") 获取环境变量内容
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/26 - 17:43
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum SystemOsProperty {

    /**
     * 定义的字典信息
     */
    SSL("ssl"),
    JAVA_VM_VERSION("java.vm.version"),
    JAVA_VENDOR("java.vendor"),
    JAVA_VERSION("java.version"),
    FILE_SEPARATOR("file.separator"),
    PATH_SEPARATOR("path.separator"),
    OS_NAME("os.name"),
    OS_VERSION("os.version"),
    USER_NAME("user.name"),
    USER_HOME("user.home"),
    USER_DIR("user.dir");

    SystemOsProperty(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
