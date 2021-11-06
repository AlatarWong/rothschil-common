package io.github.rothschil.common.down.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/6 - 10:57
 * @since 1.0.0
 */
public class FtpConf implements Serializable {

    private Long id;

    /**
     * 远程Host地址
     */
    private String host;

    /**
     * 远程端口
     */
    private String port;

    /**
     * 远程目录
     */
    private String remoteDir;

    /**
     * 本地存储目录
     */
    private String localDic;

    /**
     * 类型(FTP/SFTP)
     */
    private Integer ftpType;

    /**
     * 登录用户
     */
    private String loginUser;

    /**
     * 登录密码
     */
    private String loginPass;

    /**
     * 修改时间
     */
    private LocalDateTime modTime;

    /**
     * 重试次数
     */
    private Integer retry;

    /**
     * KEY
     */
    private String srKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public void setRemoteDir(String remoteDir) {
        this.remoteDir = remoteDir;
    }

    public String getLocalDic() {
        return localDic;
    }

    public void setLocalDic(String localDic) {
        this.localDic = localDic;
    }

    public Integer getFtpType() {
        return ftpType;
    }

    public void setFtpType(Integer ftpType) {
        this.ftpType = ftpType;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public LocalDateTime getModTime() {
        return modTime;
    }

    public void setModTime(LocalDateTime modTime) {
        this.modTime = modTime;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public String getSrKey() {
        return srKey;
    }

    public void setSrKey(String srKey) {
        this.srKey = srKey;
    }
}
