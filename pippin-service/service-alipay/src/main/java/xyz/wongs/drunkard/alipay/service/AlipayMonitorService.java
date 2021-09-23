package xyz.wongs.drunkard.alipay.service;

import com.alipay.api.response.MonitorHeartbeatSynResponse;
import xyz.wongs.drunkard.alipay.model.builder.AlipayHeartbeatSynRequestBuilder;

/** 提供交易保障服务
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @description //TODO
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 10:10
 * @version 1.0.0
 */
public interface AlipayMonitorService {

    // 交易保障接口 https://openhome.alipay.com/platform/document.htm#mobileApp-barcodePay-API-heartBeat

    // 可以提供给系统商/pos厂商使用
    MonitorHeartbeatSynResponse heartbeatSyn(AlipayHeartbeatSynRequestBuilder builder);
}
