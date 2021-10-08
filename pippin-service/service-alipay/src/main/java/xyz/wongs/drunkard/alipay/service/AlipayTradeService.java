package xyz.wongs.drunkard.alipay.service;


import xyz.wongs.drunkard.alipay.model.builder.AlipayTradePayRequestBuilder;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradeQueryRequestBuilder;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradeRefundRequestBuilder;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FPayResult;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FPrecreateResult;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FQueryResult;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FRefundResult;

/** 提供当面付2.0服务，要在创建AlipayTradeService之前调用Configs.init("xxxxxx");设置参数
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2021/9/23 - 10:11
 * @since 1.0.0
 */
public interface AlipayTradeService {

    /** 当面付2.0流程支付
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-9:43
     * @param builder
     * @return AlipayF2FPayResult
     **/
    AlipayF2FPayResult tradePay(AlipayTradePayRequestBuilder builder);

    /** 当面付2.0消费查询
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-9:43
     * @param builder
     * @return AlipayF2FPayResult
     **/
    AlipayF2FQueryResult queryTradeResult(AlipayTradeQueryRequestBuilder builder);

    /** 当面付2.0消费退款
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-9:43
     * @param builder
     * @return AlipayF2FPayResult
     **/
    AlipayF2FRefundResult tradeRefund(AlipayTradeRefundRequestBuilder builder);

    /** 当面付2.0预下单(生成二维码)
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-9:43
     * @param builder
     * @return AlipayF2FPayResult
     **/
    AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateRequestBuilder builder);
}
