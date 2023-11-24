package io.github.rothschil.common.utils;

import cn.hutool.core.util.JAXBUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.webservice.SoapClient;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.github.rothschil.common.base.dto.RestBean;
import io.github.rothschil.common.base.vo.BaseReq;
import io.github.rothschil.common.base.vo.BaseResp;
import io.github.rothschil.common.base.vo.RequestHeaderVo;
import io.github.rothschil.common.constant.Constant;
import io.github.rothschil.common.exception.CommonException;
import io.github.rothschil.common.handler.IntfLog;
import io.github.rothschil.common.intf.IntfConfEntity;
import io.github.rothschil.common.intf.IntfConfService;
import io.github.rothschil.common.queue.AppLogQueue;
import io.github.rothschil.common.response.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 构建远程 RPC 访问的工具类，对 HTTP GET/POST 以及 SOAP 的封装
 * @author HeD
 * @author  <a href="mailto:WCNGS@QQ.COM">Sam</a>
 */
@Slf4j
public class RestUtils {

    private static AppLogQueue APPLOG_QUEUE = null;

    private static IntfConfService itfConfService= null;

    /**
     * 配置工厂
     * @param timeout 超时时间
     * @return  RestTemplate
     */
    public static RestTemplate getRestTemplate(Integer timeout) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(timeout);
        requestFactory.setReadTimeout(timeout);
        return new RestTemplate(requestFactory);
    }

    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfCode    接口编码
     * @param req   入参实例
     * @param baseRespClass 响应实例的类型
     * @return T
     **/
    public static <T extends BaseResp> T post(String intfCode, BaseReq req, Class<T> baseRespClass) {
        IntfConfEntity intfConf = getIntfConf(intfCode);
        return post(intfConf,req,baseRespClass);
    }

    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConf    接口信息
     * @param req   入参实例
     * @param baseRespClass 响应实例的类型
     * @return T
     **/
    public static <T extends BaseResp> T post(IntfConfEntity intfConf, BaseReq req, Class<T> baseRespClass) {
        return post(intfConf,req,baseRespClass,new HttpHeaders());
    }


    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfCode    接口信息
     * @param req   入参实例
     * @param baseRespClass 响应实例的类型
     * @param appendHttpHeaders 根据具体业务动态添加请求Header
     * @return T
     **/
    public static <T extends BaseResp> T post(String intfCode,BaseReq req, Class<T> baseRespClass,HttpHeaders appendHttpHeaders) {
        IntfConfEntity intfConf = getIntfConf(intfCode);
        RestBean restBean = RestUtils.post(intfConf, JSON.toJSONString(req), appendHttpHeaders);
        if (restBean.getCode() != HttpStatus.OK.value()) {
            throw new CommonException(Status.FAILURE.getStatus(),baseRespClass.getSimpleName());
        }
        String respJson = restBean.getResp();
        return com.alibaba.fastjson.JSONObject.parseObject(respJson, baseRespClass);
    }

    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConfEntity    接口信息
     * @param req   入参实例
     * @param baseRespClass 响应实例的类型
     * @param appendHttpHeaders 根据具体业务动态添加请求Header
     * @return T
     **/
    public static <T extends BaseResp> T post(IntfConfEntity intfConfEntity,BaseReq req, Class<T> baseRespClass,HttpHeaders appendHttpHeaders) {
        RestBean restBean = RestUtils.post(intfConfEntity, JSON.toJSONString(req), appendHttpHeaders);
        if (restBean.getCode() != HttpStatus.OK.value()) {
            throw new CommonException(Status.FAILURE.getStatus(),baseRespClass.getSimpleName());
        }
        String respJson = restBean.getResp();
        return com.alibaba.fastjson.JSONObject.parseObject(respJson, baseRespClass);
    }

    public static RestBean post(String serviceName, String json, HttpHeaders httpHeaders) {
        IntfConfEntity intfConf = getIntfConf(serviceName);
        if (null == intfConf) {
            throw new CommonException(Status.TARGET_NOT_EXIST,"intfConf查询失败,接口未配置 serviceName:"+serviceName);
        }
        return post(intfConf, json, httpHeaders);
    }

    public static RestBean post(IntfConfEntity intfConf, String json, HttpHeaders httpHeaders) {
        boolean enabled = false;//getLogSwitchClosed();
        IntfLog intfLog = null;
        if(enabled){
            intfLog = postBuildIntfLog(intfConf,json);
        }
        int timeout = getTimeOut(intfConf);
        //请求头
        String address = intfConf.getAddress();
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = getRestTemplate(timeout);
        //返回乱码处理
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> exchange = exchange(restTemplate, httpHeaders, address, HttpMethod.POST, json, intfConf);
        RestBean restBean = getRestBean(exchange);
        if(enabled){
            afterBuildIntfLog(intfLog,restBean,start);
        }
        return restBean;
    }

    public static RestBean postXml(IntfConfEntity intfConf, String xmlContent, HttpHeaders httpHeaders) {
        boolean enabled = false; //getLogSwitchClosed();
        IntfLog intfLog = null;
        if(enabled){
            intfLog = postBuildIntfLog(intfConf, xmlContent);
        }
        int timeout = getTimeOut(intfConf);
        //请求头
        String address = intfConf.getAddress();
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = getRestTemplate(timeout);
        intfConf.getHeaderInfo();
        // 解析 intfConf.getHeaderInfo() 添加xppid和xappkey
        String headerInfo = intfConf.getHeaderInfo();
        if (StringUtils.isNotBlank(headerInfo)) {
            JSONObject jsonObject = JSONUtil.parseObj(headerInfo);
            if (jsonObject.size() > 0) {
                jsonObject.forEach((k, v) -> {
                    v = ObjectUtil.isEmpty(v) ? "" : v;
                    httpHeaders.set(k, (String) v);
                });
            }
        }
        // 设置内容类型为XML
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> requestEntity = new HttpEntity<>(xmlContent, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(address, HttpMethod.POST, requestEntity, String.class);

        RestBean restBean = getRestBean(exchange);
        if(enabled){
            afterBuildIntfLog(intfLog, restBean, start);
        }
        return restBean;
    }

    /** 构建超时时间，如果没有配置，则启用 5 秒
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConf  http配置实例
     * @return int  具体超时时间
     **/
    public static int getTimeOut(IntfConfEntity intfConf){
        int timeCout = intfConf.getTimeout();
        if(timeCout<1200){
            timeCout = 2500;
        }
        return timeCout;
    }


    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param serviceName    接口编码
     * @param params   入参实例
     * @param headerInfo 头信息
     * @return RestBean
     **/
    public static RestBean get(String serviceName, Map params, HttpHeaders headerInfo) {
        IntfConfEntity intfConf = getIntfConf(serviceName);
        if (null == intfConf) {
            throw new CommonException(Status.TARGET_NOT_EXIST,"intfConf查询失败,接口未配置 serviceName:"+serviceName);
        }
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");
        params.forEach((k, v) -> stringAtomicReference.set(stringAtomicReference + "&" + k + "=" + v));
        String uri = stringAtomicReference.get();
        if (uri.startsWith("&")) {
            uri = uri.substring(1);
            uri = "?" + uri;
        }
        return get(intfConf, uri, headerInfo);
    }

    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConf    接口信息
     * @param params   入参实例
     * @param headerInfo 头信息
     * @return RestBean
     **/
    public static RestBean get(IntfConfEntity intfConf, Map params, HttpHeaders headerInfo) {
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");
        params.forEach((k, v) -> stringAtomicReference.set(stringAtomicReference + "&" + k + "=" + v));
        String uri = stringAtomicReference.get();
        if (uri.startsWith("&")) {
            uri = uri.substring(1);
            uri = "?" + uri;
        }
        return get(intfConf, uri, headerInfo);
    }

    /** 根据请求实例获取响应
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConfEntity    接口信息
     * @param params   入参实例
     * @param baseRespClass 响应实例的类型
     * @return T
     **/
    public static <T extends BaseResp> T get(IntfConfEntity intfConfEntity,Map<String,Object> params, Class<T> baseRespClass) {
        RestBean restBean = get(intfConfEntity, params, new HttpHeaders());
        if (restBean.getCode() != HttpStatus.OK.value()) {
            throw new CommonException(Status.FAILURE.getStatus(), baseRespClass.getSimpleName());
        }
        String respJson = restBean.getResp();
        return com.alibaba.fastjson.JSONObject.parseObject(respJson, baseRespClass);
    }

    public static RestBean postSpecialHeader(String serviceName, Map map, HttpHeaders headerInfo) {
        IntfConfEntity intfConf = getIntfConf(serviceName);
        if (null == intfConf) {
            throw new CommonException(Status.TARGET_NOT_EXIST,"intfConf查询失败,接口未配置 serviceName:"+serviceName);
        }
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");
        map.forEach((k, v) -> stringAtomicReference.set(stringAtomicReference + "&" + k + "=" + v));
        String uri = stringAtomicReference.get();
        if (uri.startsWith("&")) {
            uri = uri.substring(1);
            uri = "?" + uri;
        }
        return post(intfConf, uri, headerInfo);
    }

    public static RestBean get(IntfConfEntity intfConf, String uri, HttpHeaders header) {
        String address = intfConf.getAddress() + uri;
        int timeout = getTimeOut(intfConf);
        RestTemplate restTemplate = getRestTemplate(timeout);
        ResponseEntity<String> exchange = exchange(restTemplate, header, address, HttpMethod.GET, "", intfConf);
        return getRestBean(exchange);
    }

    private static RestBean getRestBean(ResponseEntity<String> exchange) {
        RestBean restBean = new RestBean();
        if (exchange == null) {
            restBean.setCode(500);
            restBean.setResp("对外请求异常");
        } else {
            restBean.setCode(exchange.getStatusCodeValue());
            restBean.setResp(exchange.getBody());
        }
        return restBean;
    }

    /**
     * 日志交互后操作
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConf   接口响应
     * @param json  响应的JSON
     **/
    private static IntfLog postBuildIntfLog(IntfConfEntity intfConf, String json){
        IntfLog entity = null;
        entity = new IntfLog();
        entity.setIntfCode(intfConf.getInterfaceName());
        entity.setSource("IVR");
        entity.setTarget(intfConf.getInterfaceName());
        entity.setRemark(intfConf.getRemark());
        entity.setReqData(json);
        entity.setReqTime(new Date());
        RequestHeaderVo ivrVo = (RequestHeaderVo)UserTransmittableUtils.get();
        return entity;
    }


    /**
     * 请求发生前
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfLog   交互日志实例
     * @param restBean  响应Bean
     * @param start 开始时间
     **/
    private static void afterBuildIntfLog(IntfLog intfLog, RestBean restBean, long start){
        afterBuildIntfLog(intfLog,JSONUtil.toJsonStr(restBean.getResp()),start,restBean.getCode()+"");
    }


    /**
     * 请求发生前
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfLog   交互日志实例
     * @param restBean  响应Bean
     * @param start 开始时间
     **/
    private static void afterBuildIntfLog(IntfLog intfLog, String restBean, long start){
        afterBuildIntfLog(intfLog,restBean,start, Constant.NUM_1);
    }


    /**
     * 请求发生前
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfLog   交互日志实例
     * @param restBean  响应Bean
     * @param start 开始时间
     * @param state 交互的状态
     **/
    private static void afterBuildIntfLog(IntfLog intfLog, String restBean, long start,String state){
        if(ObjectUtil.isEmpty(intfLog)){
            return ;
        }
        long end = System.currentTimeMillis();
        intfLog.setProcTime(end-start);
        intfLog.setRespTime(new Date());
        intfLog.setState(state);
        intfLog.setRespData(restBean);
        // CompletableFuture.runAsync(()-> {
        //     if (null == APPLOG_QUEUE) {
        //         synchronized ("APPLOG_QUEUE_CLAZZ") {
        //             SpringUtil.getBean(AppLogQueue.class);
        //         }
        //     }
        //     APPLOG_QUEUE.addQueue(intfLog);
        // });
    }


    /**
     * 对外封装 WebService 的调用<br/>
     *
     * 1、nameSpaceHeadersMap 设置内容如下
     * <pre>
     * Map<String, String> nameSpaceHeadersMap = new LinkedHashMap();
     * nameSpaceHeadersMap.put("xsi", "<a href="http://www.w3.org/2001/XMLSchema-instance">...</a>");
     * nameSpaceHeadersMap.put("xsd", "http://www.w3.org/2001/XMLSchema");
     * nameSpaceHeadersMap.put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
     * </pre>
     *
     *
     * 2、soapClient.getMessage().getSOAPPart().getEnvelope().addNamespaceDeclaration(header.getKey(), header.getValue());
     * <pre>
     * </pre>
     *
     * 3、mapMethod 设置内容如下
     * <pre>
     *  soapClient.setMethod("impl:excuteResult","http://impl.service.label.webservice.timesontransfar.com");
     *
     *  soapClient.setMethod(key,mapMethod.get(key));
     * </pre>
     *
     * 4、mapMethodEle 设置内容如下
     * <pre>
     * methodEle.setAttribute("soapenv:encodingStyle", "http://schemas.xmlsoap.org/soap/encoding/");
     * </pre>
     *
     * 5、mapSoapElement 设置内容如下
     * <pre>
     * soapElement.setAttribute("xsi:type", "soapenc:string");
     * soapElement.setAttribute("xmlns:soapenc", "http://schemas.xmlsoap.org/soap/encoding/");
     * </pre>
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param xmlContent   请求XMl报文提体
     * @param intfConfEntity    接口配置，用于获取Http的 地址 和 请求 Header信息
     * @param nameSpaceHeadersMap  命名空间
     * @param mapMethod 执行方法名
     * @param mapMethodEle  方法元素
     * @param childElement  子节点
     * @param mapSoapElement    SOAP元素节点
     * @return RestBean
     *
     **/
    public static SoapClient post2Webservice(String xmlContent,IntfConfEntity intfConfEntity,
                                             Map<String, String> nameSpaceHeadersMap,
                                             Map<String, String> mapMethod,
                                             Map<String, String> mapMethodEle,
                                             String childElement,
                                             Map<String, String> mapSoapElement) {
        SoapClient soapClient = null;
        Map<String,String> map = JSON.parseObject(intfConfEntity.getHeaderInfo(), new TypeReference<HashMap<String, String>>() {});
        try {
            soapClient = SoapClient.create(intfConfEntity.getAddress()).header("SOAPAction","application/soap+xml;charset=utf-8").headerMap(map,true)
                    .setReadTimeout(intfConfEntity.getTimeout())
                    .setConnectionTimeout(intfConfEntity.getTimeout());
            for (Map.Entry<String, String> header : nameSpaceHeadersMap.entrySet()) {
                try {
                    soapClient.getMessage().getSOAPPart().getEnvelope().addNamespaceDeclaration(header.getKey(), header.getValue());
                } catch (SOAPException e) {
                    return null;
                }
            }
            // 设置方法
            if(!mapMethod.isEmpty()){
                for(String key:mapMethod.keySet()){
                    soapClient.setMethod(key,mapMethod.get(key));
                }
            }
            SOAPBodyElement methodEle = soapClient.getMethodEle();
            if(!mapMethodEle.isEmpty()){
                for(String key:mapMethodEle.keySet()){
                    methodEle.setAttribute(key,mapMethodEle.get(key));
                }
            }

            SOAPElement soapElement = methodEle.addChildElement(childElement);
            if(!mapSoapElement.isEmpty()){
                for(String key:mapSoapElement.keySet()){
                    soapElement.setAttribute(key, mapSoapElement.get(key));
                }
            }
            soapElement.setValue(xmlContent);
        } catch (Exception e) {
            log.error("");
        }
        return soapClient;
    }




    /**
     * 对外封装 WebService 的调用
     *
     * 1、nameSpaceHeadersMap 设置内容如下
     * <pre>
     * Map<String, String> nameSpaceHeadersMap = new LinkedHashMap();
     * nameSpaceHeadersMap.put("xsi", "<a href="http://www.w3.org/2001/XMLSchema-instance">...</a>");
     * nameSpaceHeadersMap.put("xsd", "<a href="http://www.w3.org/2001/XMLSchema">...</a>");
     * nameSpaceHeadersMap.put("soapenv", "<a href="http://schemas.xmlsoap.org/soap/envelope/">...</a>");
     *
     *
     * 2、soapClient.getMessage().getSOAPPart().getEnvelope().addNamespaceDeclaration(header.getKey(), header.getValue());
     * </pre>
     *
     * 3、mapMethod 设置内容如下
     * <pre>
     *  soapClient.setMethod("impl:excuteResult","<a href="http://impl.service.label.webservice.timesontransfar.com">...</a>");
     *
     *  soapClient.setMethod(key,mapMethod.get(key));
     * </pre>
     *
     * 4、mapMethodEle 设置内容如下
     * <pre>
     * methodEle.setAttribute("soapenv:encodingStyle", "<a href="http://schemas.xmlsoap.org/soap/encoding/">...</a>");
     * </pre>
     *
     * 5、mapSoapElement 设置内容如下
     * <pre>
     * soapElement.setAttribute("xsi:type", "soapenc:string");
     * soapElement.setAttribute("xmlns:soapenc", "<a href="http://schemas.xmlsoap.org/soap/encoding/">...</a>");
     * </pre>
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param req   请求实体
     * @param intfConfEntity    接口配置，用于获取Http的 地址 和 请求 Header信息
     * @param nameSpaceHeadersMap  命名空间
     * @param mapMethod 执行方法名
     * @param mapMethodEle  方法元素
     * @param childElement  子节点
     * @param mapSoapElement    SOAP元素节点
     * @return RestBean
     **/
    public static SoapClient post2Webservice(Object req,IntfConfEntity intfConfEntity,
                                             Map<String, String> nameSpaceHeadersMap,
                                             Map<String, String> mapMethod,
                                             Map<String, String> mapMethodEle,
                                             String childElement,
                                             Map<String, String> mapSoapElement) {

        String xmlContent = JAXBUtil.beanToXml(req);
        return post2Webservice(xmlContent,intfConfEntity,nameSpaceHeadersMap,mapMethod,mapMethodEle,childElement,mapSoapElement);
    }

    public static SoapClient post2Webservice(Object req,IntfConfEntity intfConfEntity,
                                             Charset charset,
                                             Map<String, String> nameSpaceHeadersMap,
                                             Map<String, String> mapMethod,
                                             Map<String, String> mapMethodEle,
                                             String childElement,
                                             Map<String, String> mapSoapElement) {
        String xmlContent = null;
        if (charset == null){
            xmlContent = JAXBUtil.beanToXml(req);
        }else{
            xmlContent = JAXBUtil.beanToXml(req, charset,true);
        }
        return post2Webservice(xmlContent,intfConfEntity,nameSpaceHeadersMap,mapMethod,mapMethodEle,childElement,mapSoapElement);
    }

    public static SoapClient post2Webservice(Object req,
                                             String intfCode,
                                             Charset charset,
                                             Map<String, String> nameSpaceHeadersMap,
                                             Map<String, String> mapMethod,
                                             Map<String, String> mapMethodEle,
                                             String childElement,
                                             Map<String, String> mapSoapElement) {
        IntfConfEntity intfConf = getIntfConf(intfCode);
        String xmlContent = null;
        if (charset == null){
            xmlContent = JAXBUtil.beanToXml(req);
        }else{
            xmlContent = JAXBUtil.beanToXml(req, charset,true);
        }
        return post2Webservice(xmlContent,intfConf,nameSpaceHeadersMap,mapMethod,mapMethodEle,childElement,mapSoapElement);
    }

    /**
     * 对外封装 WebService 的调用
     *
     * 1、nameSpaceHeadersMap 设置内容如下
     * <pre>
     * Map<String, String> nameSpaceHeadersMap = new LinkedHashMap();
     * nameSpaceHeadersMap.put("xsi", "<a href="http://www.w3.org/2001/XMLSchema-instance">...</a>");
     * nameSpaceHeadersMap.put("xsd", "http://www.w3.org/2001/XMLSchema");
     * nameSpaceHeadersMap.put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
     *
     *
     * 2、soapClient.getMessage().getSOAPPart().getEnvelope().addNamespaceDeclaration(header.getKey(), header.getValue());
     * </pre>
     *
     * 3、mapMethod 设置内容如下
     * <pre>
     *  soapClient.setMethod("impl:excuteResult","http://impl.service.label.webservice.timesontransfar.com");
     *
     *  soapClient.setMethod(key,mapMethod.get(key));
     * </pre>
     *
     * 4、mapMethodEle 设置内容如下
     * <pre>
     * methodEle.setAttribute("soapenv:encodingStyle", "http://schemas.xmlsoap.org/soap/encoding/");
     * </pre>
     *
     * 5、mapSoapElement 设置内容如下
     * <pre>
     * soapElement.setAttribute("xsi:type", "soapenc:string");
     * soapElement.setAttribute("xmlns:soapenc", "http://schemas.xmlsoap.org/soap/encoding/");
     * </pre>
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param req   请求实体
     * @param intfConfEntity    接口配置，用于获取Http的 地址 和 请求 Header信息
     * @param nameSpaceHeadersMap  命名空间
     * @param mapMethod 执行方法名
     * @param mapMethodEle  方法元素
     * @param childElement  子节点
     * @param mapSoapElement    SOAP元素节点
     * @return RestBean
     **/
    public static String post2WebserviceContent(Object req,IntfConfEntity intfConfEntity,
                                                Map<String, String> nameSpaceHeadersMap,
                                                Map<String, String> mapMethod,
                                                Map<String, String> mapMethodEle,
                                                String childElement,
                                                Map<String, String> mapSoapElement) {


        String xmlContent = JAXBUtil.beanToXml(req);
        /*boolean enabled = getLogSwitchClosed();*/
        boolean enabled = false;
        IntfLog intfLog = null;
        if(enabled){
            intfLog = postBuildIntfLog(intfConfEntity,xmlContent);
        }
        long start = System.currentTimeMillis();
        SoapClient soapClient = post2Webservice(xmlContent,intfConfEntity,nameSpaceHeadersMap,mapMethod,mapMethodEle,childElement,mapSoapElement);
        String soapResp = soapClient.send(false);
        if(enabled){
            afterBuildIntfLog(intfLog,soapResp,start);
        }
        return soapResp;
    }


    /** 底层接口实现
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @param restTemplate  {@link RestTemplate}
     * @param httpHeaders   头信息
     * @param address   地址
     * @param method    方法类型
     * @param json  JSON方法
     * @param intfConf  接口实例
     * @return String>
     **/
    private static ResponseEntity<String> exchange(RestTemplate restTemplate, HttpHeaders httpHeaders, String address,
                                                   HttpMethod method, String json, IntfConfEntity intfConf) {

        ResponseEntity<String> exchange = null;
        String body;
        if (StringUtils.isBlank(address)) {
            log.error("address地址为空");
            throw new CommonException(Status.NULL_POINTER_EXCEPTION,"address地址为空");
        }
        // 添加 Header 头信息
        String headerInfo = intfConf.getHeaderInfo();
        if (StringUtils.isNotBlank(headerInfo)) {
            JSONObject jsonObject = JSONUtil.parseObj(headerInfo);
            if (!jsonObject.isEmpty()) {
                jsonObject.forEach((k, v) -> {
                    v = ObjectUtil.isEmpty(v) ? "" : v;
                    httpHeaders.set(k, (String) v);
                });
            }
        }
        if (ObjectUtil.isNull(httpHeaders.getContentType())) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(json, httpHeaders);
        String errMsg=Constant.HTTP_ERR_MSG_DEFAULT;
        long beginTime = System.currentTimeMillis();
        try {
            if (httpHeaders.containsKey("POST")) {
                method = HttpMethod.POST;
            }
            exchange = restTemplate.exchange(address, method, requestEntity, String.class);
            int statusCodeValue = exchange.getStatusCodeValue();
            if (statusCodeValue == 429) {
                HttpHeaders headers = exchange.getHeaders();
                String rate = headers.getFirst("X-RateLimit-Reset");
                log.warn("429 Too Many Requests,X-RateLimit-Reset:{},{}秒后重新请求", rate, rate);
                return exchange;
            }
        } catch (HttpStatusCodeException exception) {
            int statusCode = exception.getStatusCode().value();
            errMsg="HttpStatusCodeException "+statusCode+"="+exception.getStatusCode().getReasonPhrase();
            // log.error("[HttpStatusCodeException exception] err\t{} [Intf Name]\t{}\t[Intf Desc]\t{}\n[Intf Url]\n{}\n[Requeset Body]\n{}\n", exception.getStatusCode().getReasonPhrase(),intfConf.getInterfaceName(),intfConf.getRemark(),address,json);
        } catch (Exception e) {
            errMsg="Exception "+TextUtil.exToStr(e);
            // log.error("[Request exception] err\t{} [Intf Name]\t{}\t[Intf Desc]\t{}\n[Intf Url]\n{}\n[Requeset Body]\n{}\n", TextUtil.exToStr(e),intfConf.getInterfaceName(),intfConf.getRemark(),address,json);
        } finally {
            long end = System.currentTimeMillis();
            body = null != exchange ? exchange.getBody() : errMsg;
            printConsoleLog(intfConf, address, json,body,(end - beginTime));
        }
        return exchange;
    }

    /**
     * 格式化打印，方便 <b>logstash</b> 分词处理
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param intfConf  接口信息
     * @param json  请求入参
     * @param body  响应内容
     * @param procTime  耗时
     **/
    private static void printConsoleLog(IntfConfEntity intfConf,String address, String json,String body,long procTime){
        Object obj = UserTransmittableUtils.get();
        String transId ="UNKNOW";
        if(obj instanceof RequestHeaderVo){
            RequestHeaderVo headerVo = (RequestHeaderVo)obj;
        }
        log.warn("{}.CallId\n[IntfName]\n{}\n[IntfDesc]\n{}\n[IntfUrl]\n{}\n[RequesetBody]\n{}\n[ResponseBody]\n{}\n[CostTime]\n{}ms\n",transId, intfConf.getInterfaceName(), intfConf.getRemark(), address, json, body, procTime);
    }

    public static IntfConfEntity getIntfConf(String serviceName) {
        if (null == itfConfService) {
            synchronized ("itfConfService_class") {
                itfConfService = SpringUtil.getBean(IntfConfService.class);
            }
        }
         return itfConfService.getIntf(serviceName);
//        return null;
    }



}

