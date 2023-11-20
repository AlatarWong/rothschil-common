package io.github.rothschil.common.remote.util;

import com.google.common.collect.ImmutableMap;
import com.jcraft.jsch.*;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.constant.Constants;
import io.github.rothschil.common.remote.bo.RemoteConf;
import io.github.rothschil.common.remote.bo.RemoteLogConnect;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

/**
 * 文件下载
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 13:44
 * @since 1.0.0
 */
@Slf4j
public class RemoteUtil {

    /**
     * 已经连接次数
     */
    private static long counted = 0;

    private final static int TIME_OUT = 30000;


    /**
     * 登录 FTP ，创建 {@link FTPClient} 连接客户端
     *
     * @param source FTP配置信息
     * @return FTPClient
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-13:59
     **/
    public static Map<String, Object> ftpClient(RemoteConf source) {
        RemoteLogConnect connect = new RemoteLogConnect();
        connect.setConfId(source.getId());
        connect.setConnTime(LocalDateTime.now());
        FTPClient ftpClient = null;
        Status status = Status.SUCCESS;
        try {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(TIME_OUT);
            ftpClient.setControlEncoding("utf-8");
            int port = Integer.parseInt(source.getPort());
            ftpClient.setDefaultPort(port);
            ftpClient.connect(source.getHost(), port);
            ftpClient.login(source.getLoginUser(), source.getLoginPass());
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                status = Status.USER_NOT_LOGIN_ERROR;
                log.error("未连接到FTP，用户名或密码错误!");
            } else {
                log.info("FTP 登录成功");
                String remoteDir = source.getRemoteDir();
                boolean changedir = ftpClient.changeWorkingDirectory(remoteDir);
                if (!changedir) {
                    status = Status.CHANGE_DIR_ERR;
                    log.error("FTP切换目录失败");
                }
                //主动模式
                ftpClient.enterLocalActiveMode();
            }
        } catch (SocketException socketException) {
            log.error("FTP的IP地址可能错误，请正确配置! ");
            status = Status.FTP_IP_ERR;
        } catch (IOException ioException) {
            status = Status.FTP_PORT_ERR;
            log.error("FTP的端口错误,请正确配置! ");
        } catch (Exception e) {
            counted += 1;
            if (source.getRetry() == counted) {
                log.error("重连次数超过阈值 系统名称");
                status = Status.FTP_CONNET_FAIL;
            }
            log.info("正在重新连接... 系统名称 ");
            connect(source);
            return ImmutableMap.of(Constants.CONN_BO, connect, Constants.CONNECT_STATUS, status);
        }
        connect.setReponTime(LocalDateTime.now());
        connect.setResult(status.getStatus());
        connect.setReq(status.getMsg());
        return ImmutableMap.of(Constants.CONN_BO, connect, Constants.CONNECT_CLINET, ftpClient, Constants.CONNECT_STATUS, status);
    }

    /**
     * 登录 SFTP ，创建 {@link FTPClient} 连接客户端
     *
     * @param source SFTP配置信息
     * @return ChannelSftp
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-17:27
     **/
    public static Map<String, Object> connect(RemoteConf source) {
        RemoteLogConnect connect = new RemoteLogConnect();
        connect.setConfId(source.getId());
        connect.setConnTime(LocalDateTime.now());
        Status status = Status.SUCCESS;
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            int port = Integer.parseInt(source.getPort());
            Session session = jsch.getSession(source.getLoginUser(), source.getHost());
            session.setPassword(source.getLoginPass());
            session.setPort(port);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.setTimeout(3000);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("SFTP 登录成功 ");
        } catch (JSchException e) {
            status = Status.FTP_SESSION;
            counted += 1;
            if (source.getRetry() == counted) {
                log.error("重连次数超过阈值 ");
                status = Status.FTP_CONNET_FAIL;
            } else {
                log.info("正在重新连接.... 次数 {}", counted);
                connect(source);
            }
            buildLogConnect(connect,status);
            return ImmutableMap.of(Constants.CONN_BO, connect, Constants.CONNECT_STATUS, status);
        }
        buildLogConnect(connect,status);
        return ImmutableMap.of(Constants.CONN_BO, connect, Constants.CONNECT_CLINET, sftp, Constants.CONNECT_STATUS, status);
    }


    public static void buildLogConnect(RemoteLogConnect connect, Status status){
        connect.setReponTime(LocalDateTime.now());
        connect.setResult(status.getStatus());
        connect.setReq(status.getMsg());
    }

    /**
     * 断掉连接
     *
     * @param sftp SFTP 实例
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-17:33
     **/
    public static void disConnect(ChannelSftp sftp) {
        try {
            sftp.disconnect();
            sftp.getSession().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断路径是否存在，不存在则创建，创建失败 则False
     *
     * @param localPath 本地路径
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-16:50
     **/
    public static void createDir(String localPath) {
        File entryDir = new File(localPath);
        //如果文件夹路径不存在，则创建文件夹
        if (!entryDir.exists() || !entryDir.isDirectory()) {
            entryDir.mkdirs();
        }
    }
}
