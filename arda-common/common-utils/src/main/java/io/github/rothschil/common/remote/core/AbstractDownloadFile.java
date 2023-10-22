package io.github.rothschil.common.remote.core;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.constant.Constants;
import io.github.rothschil.common.remote.bo.DownloadingBo;
import io.github.rothschil.common.remote.bo.FileEntry;
import io.github.rothschil.common.remote.bo.FileTransferProtocolConf;
import io.github.rothschil.common.remote.bo.RemoteLogConnect;
import io.github.rothschil.common.remote.filter.GrpFilter;
import io.github.rothschil.common.remote.util.RemoteUtil;
import io.github.rothschil.common.remote.util.FileSizeUtil;
import io.github.rothschil.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 抽象文件下载，提供通用方法
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 18:07
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractDownloadFile {


    /**
     * 检查文件名称是否符合规范
     *
     * @param fileName 系统名称
     * @param contains 文件名称规则
     * @return boolean
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-2:47
     **/
    protected boolean checkFileName(String fileName, String contains) {
        String tmp = fileName.toUpperCase();
        return tmp.contains(contains.toUpperCase());
    }


    /**
     * 检查文件名是否符合 系统配置 的规则
     *
     * @param source   系统配置
     * @param fileName 文件名
     * @return String
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-2:47
     **/
    public boolean vilateName(FileTransferProtocolConf source, String fileName) {
        String contains = getContains(source);
        // 文件列表中不包含当前日期的文件
        if (!checkFileName(fileName, contains)) {
            log.warn("fileName={},contains={}", fileName, contains);
            return false;
        }
        return true;
    }

    /**
     * 拼接本地路径
     *
     * @param source   系统配置
     * @param fileName 文件名
     * @return String
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-2:47
     **/
    public String getLocalFileName(FileTransferProtocolConf source, String fileName) {
        String localPath = source.getLocalDic();
        return checkPath(localPath) + fileName;
    }

    /**
     * 当前系统 的文件名称 前缀，获取 以当前时间为参考点，去前一天
     *
     * @param source 系统配置
     * @return String 类似 crm.2021-10-01
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-2:47
     **/
    public String getContains(FileTransferProtocolConf source) {
        return Constants.HF_PERIOD + DateUtils.offset(-1,DateUtils.DAY_PATTERN);
    }

    /**
     * 将远程路径和文件名 进行拼接，注意 文件分隔符
     *
     * @param remoteFilePath 远程路径
     * @param fileName       文件名
     * @return String 远程文件路径 + / +文件名
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-2:50
     **/
    protected String getRemoteFilePath(String remoteFilePath, String fileName) {
        if (remoteFilePath.endsWith(Constants.SLASH)) {
            return remoteFilePath.concat(fileName);
        } else {
            return remoteFilePath.concat(Constants.SLASH).concat(fileName);
        }
    }

    /**
     * 路径没有文件路径分隔符，则需要添加
     *
     * @param path 源文件
     * @return String 有文件路径分隔符的路径
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-23:11
     **/
    protected String checkPath(String path) {
        String tmp = path;
        if (!path.endsWith(File.separator)) {
            tmp = path + File.separator;
        }
        return tmp;
    }

    /**
     * 文件过滤器
     *
     * @param source    系统配置
     * @param ftpClient ftp连接客户端
     * @return FTPFile[]
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-2:56
     **/
    protected FTPFile[] getFtpFiles(FileTransferProtocolConf source, FTPClient ftpClient) {
        String contains = getContains(source);
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles(Constants.HF_PERIOD, new GrpFilter(contains));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    protected FileEntry build(String transId, FileTransferProtocolConf source, FTPFile tmpFile) {
        String fileName = tmpFile.getName();
        FileEntry file = new FileEntry();
        file.setFileName(fileName);
        file.setFileSize(tmpFile.getSize()+"");
        file.setRemotePath(source.getRemoteDir());
        file.setTransId(transId);
        file.setLocalPath(source.getLocalDic() + fileName);
        return file;
    }

    public void insertRecord(DownloadingBo bo) {
        List<FileEntry> lists = bo.getLists();
        if (!lists.isEmpty()) {
            return;
        }
        RemoteLogConnect connect = bo.getRemoteLogConnect();
        Status status = bo.getStatus();
        connect.setDownStatus(status.getStatus());
        connect.setTransId(bo.getTransId());
        connect.setStatusDetails(status.toString());
        connect.setAmount(lists.size());
        log.error("已下载文件数量 {}",lists.size());
    }

    /**
     * 检查配置的路径后缀是否有文件分隔符，没有的，则需要添加
     *
     * @param source 源配置
     * @return String   有文件路径分隔符的路径
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-23:11
     **/
    protected String checkPath(FileTransferProtocolConf source) {
        String temp = source.getLocalDic();
        return checkPath(temp);
    }

    /**
     * 关闭流
     *
     * @param outputStream 输出流
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-17:30
     **/
    protected void closeStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * FTP 方式下载文件
     *
     * @param transId 流水
     * @param source  接入系统配置
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-3:08
     **/
    protected DownloadingBo downloadFtp(String transId, FileTransferProtocolConf source) {
        Map<String, Object> map = RemoteUtil.ftpClient(source);
        List<FileEntry> lists = new ArrayList<>();
        OutputStream outputStream = null;
        Status status = (Status)map.get(Constants.CONNECT_STATUS);
        RemoteLogConnect remoteLogConnect = (RemoteLogConnect) map.get(Constants.CONN_BO);
        try {
            if(!status.getStatus().equals(Status.SUCCESS.getStatus())){
                return DownloadingBo.builder().remoteLogConnect(remoteLogConnect).status(status).lists(Collections.emptyList()).build();
            }
            FTPClient ftpClient = (FTPClient) map.get(Constants.CONNECT_CLINET);
            String contains = getContains(source);
            FTPFile[] files = new FTPFile[0];
            try {
                files = ftpClient.listFiles(Constants.HF_PERIOD, new GrpFilter(contains));
            } catch (IOException e) {
                status = Status.UNMATCHED_FILE;
            }
            if (null == files || files.length == 0) {
                log.error("没有符合条件的文件 文件 {} ", contains);
                status = Status.UNMATCHED_FILE;
            } else {
                String localPath = checkPath(source);
                RemoteUtil.createDir(source.getLocalDic());
                for (FTPFile tmpFile : files) {
                    String fileName = tmpFile.getName();
                    String localFilePath = localPath + fileName;
                    File localFile = new File(localFilePath);
                    //文件下载全路径 包含名字
                    //判断文件在本地是否存在，存在则继续，不覆盖本地文件
                    if (localFile.exists()) {
                        continue;
                    }
                    lists.add(build(transId,source, tmpFile));
                    log.debug("fileName={},localFilePath={}", fileName, localFilePath);
                    outputStream = new FileOutputStream(localFilePath);
                    ftpClient.retrieveFile(fileName, outputStream);
                }
            }
        } catch (IOException e) {
            status = Status.DOWNLOAD_ERR;
        } finally {
            closeStream(outputStream);
        }
        return DownloadingBo.builder().remoteLogConnect(remoteLogConnect).lists(lists).status(status).build();
    }

    /**
     * SFTP 方式下载文件
     *
     * @param transId 流水
     * @param source 接入系统配置
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/24-3:08
     **/
    protected DownloadingBo downloadSftp(String transId, FileTransferProtocolConf source) {
        Map<String, Object> map = RemoteUtil.connect(source);
        RemoteLogConnect remoteLogConnect = (RemoteLogConnect) map.get(Constants.CONN_BO);
        Status status = (Status)map.get(Constants.CONNECT_STATUS);
        List<FileEntry> lists = new ArrayList<>();
        try {
            if(!status.getStatus().equals(Status.SUCCESS.getStatus())){
                return DownloadingBo.builder().remoteLogConnect(remoteLogConnect).status(status).lists(Collections.emptyList()).build();
            }
            ChannelSftp chSftp = (ChannelSftp)map.get(Constants.CONNECT_CLINET);
            String remote = source.getRemoteDir();
            Vector<ChannelSftp.LsEntry> vectors = chSftp.ls(remote);
            if (vectors.isEmpty()) {
                status = Status.REMOTE_EMPTY;
                return DownloadingBo.builder().remoteLogConnect(remoteLogConnect).status(status).lists(Collections.emptyList()).build();
            }
            RemoteUtil.createDir(source.getLocalDic());
            for (ChannelSftp.LsEntry entry : vectors) {
                SftpATTRS attrs = entry.getAttrs();
                if (attrs.isDir()) {
                    // 可能是目录，暂时不做处理
                    continue;
                }
                String fileName = entry.getFilename();
                if (!vilateName(source, fileName)) {
                    continue;
                }

                //文件大于等于0则存在
                String fileSize;
                try {
                    fileSize = FileSizeUtil.formatFileSize(attrs.getSize());
                } catch (Exception e) {
                    //获取文件大小异常
                    fileSize = "-1";
                    if (e.getMessage().equalsIgnoreCase("no such file")) {
                        //文件不存在
                        fileSize = "-2";
                    }
                }

                //远程文件路径+名称
                String localFilePath = getLocalFileName(source, fileName);
                //本地路径+文件名
                String remoteFilePath = getRemoteFilePath(remote, fileName);

                FileEntry file = new FileEntry();
                file.setTransId(transId);
                file.setFileName(fileName);
                file.setRemotePath(remoteFilePath);
                file.setLocalPath(localFilePath);
                file.setFileSize(fileSize+"");
                lists.add(file);
                log.debug("fileName={},remoteFilePath={},localFilePath={},fileSize={}", fileName, remoteFilePath, localFilePath,fileSize);
                chSftp.get(remoteFilePath, localFilePath);
            }
            RemoteUtil.disConnect(chSftp);
        } catch (SftpException e) {
            status = Status.DOWNLOAD_ERR;
        }
        return DownloadingBo.builder().remoteLogConnect(remoteLogConnect).lists(lists).status(status).build();
    }
}
