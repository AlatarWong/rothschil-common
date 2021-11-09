package io.github.rothschil.base.elastic.bo;

/**
 * 手工创建索引，并且指定分片 和 副本
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/26 - 10:17
 * @since 1.0.0
 */
public class EsData {

    public static final String DEFAULT_SETTING = "{\"dynamic\":false,\"properties\":{\"logId\":{\"type\":\"keyword\",\"index\":false},\"time\":{\"type\":\"keyword\",\"index\":false},\"uri\":{\"type\":\"keyword\",\"index\":true},\"host\":{\"type\":\"keyword\",\"index\":true},\"request\":{\"type\":\"keyword\",\"index\":false},\"requestUri\":{\"type\":\"keyword\",\"index\":false},\"requestBody\":{\"type\":\"text\",\"index\":false},\"requestMethod\":{\"type\":\"keyword\",\"index\":false},\"requestTime\":{\"type\":\"keyword\",\"index\":false},\"upstreamResponseTime\":{\"type\":\"keyword\",\"index\":false},\"isArgs\":{\"type\":\"keyword\",\"index\":false},\"args\":{\"type\":\"keyword\",\"index\":false},\"status\":{\"type\":\"integer\",\"index\":true},\"uid\":{\"type\":\"keyword\",\"index\":false},\"httpHost\":{\"type\":\"keyword\",\"index\":true},\"referer\":{\"type\":\"keyword\",\"index\":false},\"ua\":{\"type\":\"keyword\",\"index\":false},\"upstreamAddr\":{\"type\":\"keyword\",\"index\":false},\"proxyAddXForwardedFor\":{\"type\":\"keyword\",\"index\":false},\"httpXForwardedFor\":{\"type\":\"keyword\",\"index\":false},\"nginxVersion\":{\"type\":\"keyword\",\"index\":false},\"rule\":{\"type\":\"keyword\",\"index\":true},\"clean\":{\"type\":\"keyword\",\"index\":true},\"sysCode\":{\"type\":\"keyword\",\"index\":true},\"sysName\":{\"type\":\"keyword\",\"index\":false}}}";
}
