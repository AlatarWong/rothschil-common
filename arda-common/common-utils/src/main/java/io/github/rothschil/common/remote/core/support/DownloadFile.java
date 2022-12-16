package io.github.rothschil.common.remote.core.support;

import io.github.rothschil.common.remote.bo.DownloadingBo;
import io.github.rothschil.common.remote.bo.FileTransferProtocolConf;
import io.github.rothschil.common.enums.FtpEnum;
import io.github.rothschil.common.remote.core.AbstractDownloadFile;
import io.github.rothschil.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 下载采集文件核心方法，获取所有已配置的接入系统，甄别 FTP/SFTP 分别调用不同的方法下载文件
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 13:57
 * @since 1.0.0
 */
@Slf4j
public class DownloadFile extends AbstractDownloadFile {


    /**
     * 此处区分 FTP 和 SFTP 分别调用不同的方法
     *
     * @param conf 系统配置列表
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-16:29
     **/
    public void download(FileTransferProtocolConf conf) {
        DownloadingBo bo;
        String transId = DateUtils.transId();
        if (conf.getFtpType() == FtpEnum.FTP.getCode()) {
            bo = downloadFtp(transId,conf);
        } else {
            bo = downloadSftp(transId,conf);
        }
        bo.setTransId(transId);
        insertRecord(bo);
    }

    /**
     * 此处区分 FTP 和 SFTP 分别调用不同的方法
     *
     * @param confSources 系统配置列表
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/23-16:29
     **/
    public void download(List<FileTransferProtocolConf> confSources) {
        for (FileTransferProtocolConf conf : confSources) {
            download(conf);
        }
    }

}
