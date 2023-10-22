package io.github.rothschil.common.remote.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 接口列表
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @since 1.0.0
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteIntf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口标识
     */
    private Integer id;

    /**
     * 系统标识
     */
    private Integer sysId;

    /**
     * 系统名称
     */
    private String intfName;

    /**
     * 系统编码（保证唯一性）
     */
    private String intfCode;

    /**
     * 接口类型：SOAP 、HTTP等
     */
    private String intfType;

    /**
     * 接口URL
     */
    private String address;

    /**
     * 备用接口URL
     */
    private String backupUrl;

    /**
     * 调用方法  POST、GET、PUT、DELETE
     */
    private String method;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 最后交互时间
     */
    private Date lastTime;

    /**
     * 最大重送次数
     */
    private int maxResend;

    /**
     * 超时时间，秒
     */
    private int timeOut;

    /**
     * 0-开启；1-关闭
     */
    private String threshold;

    /**
     * 开关关闭原因
     */
    private String reason;

    /**
     * 接口描述
     */
    private String remark;

    /**
     * 头信息
     */
    private List<RemoteIntfHeader> headers;

}
