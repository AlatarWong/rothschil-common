package xyz.wongs.drunkard.common.enums;

/**
 * 定制系统基本信息包括JDK版本以及当前路径等
 * @author WCNGS@QQ.COM
 * @ClassName SysProperty$
 * @Description
 * 
 * @date 21/3/26$ 09:35$
 * @Version 1.0.0
 */
public enum SysProperty {

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

    SysProperty(String code){
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
