package io.github.rothschil.common.utils.rest;

import cn.hutool.core.util.ObjectUtil;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.exception.DrunkardException;
import io.github.rothschil.common.remote.bo.RemoteIntf;
import io.github.rothschil.common.remote.bo.RemoteIntfHeader;
import io.github.rothschil.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @since 1.0.0
 */
@Slf4j
@Component
public class RestUtils {

    /**
     * 配置工厂
     *
     * @param timeout 超时时间
     * @return RestTemplate
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    public static RestTemplate getRestTemplate(Integer timeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(timeout);
        requestFactory.setReadTimeout(timeout);
        return new RestTemplate(requestFactory);
    }

    public static RemoteIntf getIntfConf(String serviceName) {
        return null;
    }

    /**
     * @param serviceName 服务名
     * @param json        String JSON字符串
     * @param httpHeaders Http 头信息
     * @return RestBean
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    public static RestBean post(String serviceName, String json, HttpHeaders httpHeaders) {
        RemoteIntf intfConf = getIntfConf(serviceName);
        if (ObjectUtil.isEmpty(intfConf)) {
            throw new DrunkardException(Status.API_EXCEPTION, serviceName);
        }
        return post(intfConf, json, httpHeaders);
    }

    /**
     * @param intf        RemoteIntf数据实例
     * @param json        String JSON字符串
     * @param httpHeaders Http 头信息
     * @return RestBean
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    public static RestBean post(RemoteIntf intf, String json, HttpHeaders httpHeaders) {
        int timeout = Optional.of(intf.getTimeOut()).orElse(15000);
        //请求头
        RestTemplate restTemplate = getRestTemplate(timeout);
        ResponseEntity<String> exchange = exchange(restTemplate, httpHeaders, json, intf);
        return getRestBean(exchange);
    }

    /**
     * Get 方法，可以对外暴露的方法
     *
     * @param serviceName 服务编码
     * @param map         请求参数
     * @param headerInfo  HttpHeaders 头信息
     * @return RestBean
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    public static RestBean get(String serviceName, Map<String, String> map, HttpHeaders headerInfo) {
        RemoteIntf intfConf = getIntfConf(serviceName);
        if (ObjectUtil.isEmpty(intfConf)) {
            throw new DrunkardException(Status.API_EXCEPTION, serviceName);
        }
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");
        map.forEach((k, v) -> stringAtomicReference.set(stringAtomicReference + "&" + k + "=" + v));
        String uri = stringAtomicReference.get();
        if (uri.startsWith("&")) {
            uri = uri.substring(1);
            uri = "?" + uri;
        }
        assert intfConf != null;
        intfConf.setAddress(uri);
        return get(intfConf, headerInfo);
    }

    /**
     * Get 方法
     *
     * @param intf   接口实例
     * @param header HttpHeaders 头信息
     * @return RestBean
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    private static RestBean get(RemoteIntf intf, HttpHeaders header) {
        int timeout = Optional.of(intf.getTimeOut()).orElse(5000);
        //请求头
        RestTemplate restTemplate = getRestTemplate(timeout);
        ResponseEntity<String> exchange = exchange(restTemplate, header, "", intf);
        return getRestBean(exchange);
    }

    private static RestBean getRestBean(ResponseEntity<String> exchange) {
        RestBean restBean = new RestBean();
        restBean.setCode(exchange.getStatusCodeValue());
        restBean.setResp(exchange.getBody());
        return restBean;
    }

    /**
     * @param restTemplate RestTemplate 实例
     * @param httpHeaders  头信息
     * @param json         请求内容
     * @param intf         接口实例
     * @return String>
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    private static ResponseEntity<String> exchange(RestTemplate restTemplate, HttpHeaders httpHeaders,
                                                   String json, RemoteIntf intf) {
        if (ObjectUtil.isEmpty(intf)) {
            log.error("[ API ] is null");
            throw new DrunkardException(Status.API_NOT_FOUND_EXCEPTION);
        }
        //数据库配置优先级最高 其次为配置文件
        buildHeader(httpHeaders, intf);
        return execute(restTemplate, httpHeaders, json, intf);
    }

    /**
     * 关联 ResponseEntity 处理
     *
     * @param restTemplate RestTemplate 实例
     * @param httpHeaders  头信息
     * @param json         请求内容
     * @param intf         接口实例
     * @return String>
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    private static ResponseEntity<String> execute(RestTemplate restTemplate, HttpHeaders httpHeaders, String json, RemoteIntf intf) {
        ResponseEntity<String> exchange = null;
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(json, httpHeaders);
            exchange = restTemplate.exchange(intf.getAddress(), HttpUtils.httpMethod(intf), requestEntity, String.class);
            int statusCodeValue = exchange.getStatusCodeValue();
            if (statusCodeValue == 429) {
                HttpHeaders headers = exchange.getHeaders();
                String rate = headers.getFirst("X-RateLimit-Reset");
                log.error("429 Too Many Requests,X-RateLimit-Reset:{},{}秒后重新请求", rate, rate);
                return exchange;
            }
        } catch (Exception e) {
            log.error("Request is Err  msg is {}, url is {} ,params is {}", e.getMessage(), intf.getAddress(), json);
        }
        String body = null != exchange ? exchange.getBody() : "error";
        log.info("Intf Code is {} ,Response:{} Request :{}", intf.getIntfCode(), body, json);
        return exchange;
    }


    /**
     * 构建请求头信息
     *
     * @param httpHeaders 头信息
     * @param intf        Intf接口实例
     * @author <a href="https://github.com/rothschil">Sam</a>
     **/
    private static void buildHeader(HttpHeaders httpHeaders, RemoteIntf intf) {
        List<RemoteIntfHeader> intfHeaders = intf.getHeaders();
        if (!intfHeaders.isEmpty()) {
            intfHeaders.forEach((hs) -> {
                httpHeaders.set(hs.getHdKey(), hs.getHdValue());
            });
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }
}
