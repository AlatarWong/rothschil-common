package xyz.wongs.drunkard.alipay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.MonitorHeartbeatSynRequest;
import com.alipay.api.response.MonitorHeartbeatSynResponse;
import org.apache.commons.lang.StringUtils;
import xyz.wongs.drunkard.alipay.config.PayConst;
import xyz.wongs.drunkard.alipay.model.builder.AlipayHeartbeatSynRequestBuilder;
import xyz.wongs.drunkard.alipay.service.AlipayMonitorService;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.property.PropConfig;

/** 提供交易保障服务
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2018/4/23 - 10:10
 * @since 1.0.0
 */
public class AlipayMonitorServiceImpl extends AbsAlipayService implements AlipayMonitorService {
    private AlipayClient client;

    public static class ClientBuilder {
        private String gatewayUrl;
        private String appid;
        private String privateKey;
        private String format;
        private String charset;
        private String signType;

        public AlipayMonitorServiceImpl build() {
            if (StringUtils.isEmpty(gatewayUrl)) {
                // 与openapi网关地址不同
                gatewayUrl = PropConfig.getProperty(PayConst.API_MC);
            }
            if (StringUtils.isEmpty(appid)) {
                appid = PropConfig.getProperty(PayConst.APPID);
            }
            if (StringUtils.isEmpty(privateKey)) {
                privateKey = PropConfig.getProperty(PayConst.PRI_KEY);
            }
            if (StringUtils.isEmpty(format)) {
                format = "json";
            }
            if (StringUtils.isEmpty(charset)) {
                charset = Constants.UTF8;
            }
            if (StringUtils.isEmpty(signType)) {
                signType = PropConfig.getProperty(PayConst.SIGN_TYPE);
            }
            return new AlipayMonitorServiceImpl(this);
        }

        public ClientBuilder setAppid(String appid) {
            this.appid = appid;
            return this;
        }

        public ClientBuilder setCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public ClientBuilder setFormat(String format) {
            this.format = format;
            return this;
        }

        public ClientBuilder setGatewayUrl(String gatewayUrl) {
            this.gatewayUrl = gatewayUrl;
            return this;
        }

        public ClientBuilder setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }
        
        public ClientBuilder setSignType(String signType) {
            this.signType = signType;
            return this;
        }
        
        public String getAppid() {
            return appid;
        }

        public String getCharset() {
            return charset;
        }

        public String getFormat() {
            return format;
        }

        public String getGatewayUrl() {
            return gatewayUrl;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public String getSignType() {
            return signType;
        }

    }

    public AlipayMonitorServiceImpl(ClientBuilder builder) {
        if (StringUtils.isEmpty(builder.getGatewayUrl())) {
            throw new NullPointerException("gatewayUrl should not be NULL!");
        }
        if (StringUtils.isEmpty(builder.getAppid())) {
            throw new NullPointerException("appid should not be NULL!");
        }
        if (StringUtils.isEmpty(builder.getPrivateKey())) {
            throw new NullPointerException("privateKey should not be NULL!");
        }
        if (StringUtils.isEmpty(builder.getFormat())) {
            throw new NullPointerException("format should not be NULL!");
        }
        if (StringUtils.isEmpty(builder.getCharset())) {
            throw new NullPointerException("charset should not be NULL!");
        }
        if (StringUtils.isEmpty(builder.getSignType())) {
            throw new NullPointerException("signType should not be NULL!");
        }

        // 此处不需要使用alipay public key，因为金融云不产生签名
        client = new DefaultAlipayClient(builder.getGatewayUrl(), builder.getAppid(), builder.getPrivateKey(),
                builder.getFormat(), builder.getCharset(), builder.getSignType());
    }

    @Override
    public MonitorHeartbeatSynResponse heartbeatSyn(AlipayHeartbeatSynRequestBuilder builder) {
        validateBuilder(builder);

        MonitorHeartbeatSynRequest request = new MonitorHeartbeatSynRequest();
        request.putOtherTextParam("app_auth_token", builder.getAppAuthToken());
        request.setBizContent(builder.toJsonString());
        log.info("heartbeat.sync bizContent:" + request.getBizContent());

        return (MonitorHeartbeatSynResponse) getResponse(client, request);
    }
}
