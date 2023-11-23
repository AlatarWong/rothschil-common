package io.github.rothschil.common.base.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户端请求基本信息
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 */
@Builder
@Data
public class RequestHeaderVo implements Serializable {

    /**
     * 客户端IP地址
     */
    private String origClientIp;
    /**
     * 客户端类型
     */
    private String userAgent;
}
