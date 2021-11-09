package io.github.rothschil.base.elastic.bo;

import io.github.rothschil.base.elastic.util.EsFlag;
import lombok.Data;

/**
 * 日志的结构
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/21 - 12:19
 * @since 1.0.0
 */
public class NgxLog {

    private String logId;
    private String time;
    private String uri;
    private String host;
    private String request;
    private String requestUri;
    private String requestBody;
    private String requestMethod;
    private String requestTime;
    private String upstreamResponseTime;
    private String isArgs;
    private String args;
    private Integer status;
    private String uid;
    private String httpHost;
    private String referer;
    private String ua;
    private String upstreamAddr;
    private String proxyaddxforwardedFor;
    private String httpXForwardedFor;
    private String nginxVersion;
    private String rule;
    private String clean;
    private String sysCode;
    private String sysName;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getUpstreamResponseTime() {
        return upstreamResponseTime;
    }

    public void setUpstreamResponseTime(String upstreamResponseTime) {
        this.upstreamResponseTime = upstreamResponseTime;
    }

    public String getIsArgs() {
        return isArgs;
    }

    public void setIsArgs(String isArgs) {
        this.isArgs = isArgs;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUpstreamAddr() {
        return upstreamAddr;
    }

    public void setUpstreamAddr(String upstreamAddr) {
        this.upstreamAddr = upstreamAddr;
    }

    public String getProxyaddxforwardedFor() {
        return proxyaddxforwardedFor;
    }

    public void setProxyaddxforwardedFor(String proxyaddxforwardedFor) {
        this.proxyaddxforwardedFor = proxyaddxforwardedFor;
    }

    public String getHttpXForwardedFor() {
        return httpXForwardedFor;
    }

    public void setHttpXForwardedFor(String httpXForwardedFor) {
        this.httpXForwardedFor = httpXForwardedFor;
    }

    public String getNginxVersion() {
        return nginxVersion;
    }

    public void setNginxVersion(String nginxVersion) {
        this.nginxVersion = nginxVersion;
    }

    public String getRule(EsFlag esFlag) {
        if (null == rule) {
            rule = EsFlag.DEFAULT_FLAG;
        }
        return rule;
    }
    public void setRule(String rule) {
        this.rule = rule;
    }


    public String getClean() {
        if (null == clean) {
            clean = EsFlag.DEFAULT_FLAG;
        }
        return clean;
    }

    public void setClean(String clean) {
        this.clean = clean;
    }



    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    @Override
    public String toString() {
        return "NgxLog{" +
                "logId='" + logId + '\'' +
                ", time='" + time + '\'' +
                ", uri='" + uri + '\'' +
                ", host='" + host + '\'' +
                ", request='" + request + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", upstreamResponseTime='" + upstreamResponseTime + '\'' +
                ", isArgs='" + isArgs + '\'' +
                ", args='" + args + '\'' +
                ", status=" + status +
                ", uid='" + uid + '\'' +
                ", httpHost='" + httpHost + '\'' +
                ", referer='" + referer + '\'' +
                ", ua='" + ua + '\'' +
                ", upstreamAddr='" + upstreamAddr + '\'' +
                ", proxyaddxforwardedFor='" + proxyaddxforwardedFor + '\'' +
                ", httpXForwardedFor='" + httpXForwardedFor + '\'' +
                ", nginxVersion='" + nginxVersion + '\'' +
                ", rule='" + rule + '\'' +
                ", clean='" + clean + '\'' +
                ", sysCode='" + sysCode + '\'' +
                ", sysName='" + sysName + '\'' +
                '}';
    }
}
