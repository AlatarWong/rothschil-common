package io.github.rothschil.common.enums;

/** FTP类型枚举
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 14:37
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum FtpEnum {

    /**
     * FTP类型
     */
    FTP(41,"FTP"),


    /**
     * SFTP类型
     */
    SFTP(42,"SFTP");

    /**
     *
     */
    private int code;
    private String coment;


    FtpEnum(int code, String coment) {
        this.code = code;
        this.coment = coment;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }
}
