package io.github.rothschil.domain.entity;

import io.github.rothschil.common.po.BasePo;
import lombok.*;

/**
 * 日志的结构
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/21 - 12:19
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NgxLog extends BasePo<Long> {

    private Long id;
    private String time;
    private String uri;
    private String host;
//    private String request;
//    private String requestUri;
//    private String requestBody;
    private String requestMethod;
    private String requestTime;
    private String upstreamResponseTime;
//    private String isArgs;
//    private String args;
    private Integer status;
    private String uid;
    private String httpHost;
//    private String referer;
    private String ua;
    private String upstreamAddr;
    private String proxyaddxforwardedFor;
    private String httpXForwardedFor;
    private String nginxVersion;



    @Override
    public String toString() {
        return "NgxLog{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", uri='" + uri + '\'' +
                ", host='" + host + '\'' +
//                ", request='" + request + '\'' +
//                ", requestUri='" + requestUri + '\'' +
//                ", requestBody='" + requestBody + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", upstreamResponseTime='" + upstreamResponseTime + '\'' +
//                ", isArgs='" + isArgs + '\'' +
//                ", args='" + args + '\'' +
                ", status=" + status +
                ", uid='" + uid + '\'' +
                ", httpHost='" + httpHost + '\'' +
//                ", referer='" + referer + '\'' +
                ", ua='" + ua + '\'' +
                ", upstreamAddr='" + upstreamAddr + '\'' +
                ", proxyaddxforwardedFor='" + proxyaddxforwardedFor + '\'' +
                ", httpXForwardedFor='" + httpXForwardedFor + '\'' +
                ", nginxVersion='" + nginxVersion + '\'' +
                '}';
    }
}
