package io.github.rothschil.common.enums;

/**
 * 获取操作系统级别的变量，可以通过 System.getProperty 方法获取 获取环境变量内容
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

    /**
     * Java 虚拟机实现版本
     */
    JAVA_VM_VERSION("java.vm.version"),

    /**
     * Java 虚拟机实现供应商
     */
    JAVA_VM_VENDOR("java.vm.version"),

    /**
     * 运行时环境供应商
     */
    JAVA_VENDOR("java.vendor"),
    /**
     * JAVA运行时环境版本
     */
    JAVA_VERSION("java.version"),

    /**
     * 文件分隔符(在 UNIX 系统中是 / )
     */
    FILE_SEPARATOR("file.separator"),

    /**
     * 路径分隔符(在 UNIX 系统中是 : )
     */
    PATH_SEPARATOR("path.separator"),
    /**
     * 行分隔符(在 UNIX 系统中是 /n )
     */
    LINE_SEPARATOR("line.separator"),
    /**
     * 操作系统名称
     */
    OS_NAME("os.name"),

    /**
     * 操作系统的版本
     */
    OS_VERSION("os.version"),

    /**
     * 用户账户名称
     */
    USER_NAME("user.name"),

    /**
     * 用户主目录
     */
    USER_HOME("user.home"),

    /**
     * 用户当前工作目录
     */
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
