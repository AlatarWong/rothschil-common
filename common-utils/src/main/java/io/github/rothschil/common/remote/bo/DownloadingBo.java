package io.github.rothschil.common.remote.bo;

import io.github.rothschil.base.response.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文件下载的中间过程实例
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/25 - 11:15
 * @since 1.0.0
 */
@Data
@Builder
public class DownloadingBo {

    private String transId;
    /**
     * 连接记录
     */
    private RemoteLogConnect remoteLogConnect;

    /**
     * 文件列表
     */
    private List<FileEntry> lists;

    /**
     * 下载处理状态
     */
    private Status status;

}
