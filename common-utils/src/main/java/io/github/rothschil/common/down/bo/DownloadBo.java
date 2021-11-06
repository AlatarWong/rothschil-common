package io.github.rothschil.common.down.bo;

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
public class DownloadBo {

    private String transId;
    /**
     * 连接记录
     */
    private LogConnect logConnect;

    /**
     * 文件列表
     */
    private List<FileList> lists;

    /**
     * 下载处理状态
     */
    private Status status;

    public String getTransId() {
        return transId;
    }
}
