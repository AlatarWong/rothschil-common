package io.github.rothschil.alipay.service.impl.hb;

/** 交易监听接口，在当面付交易流程中加入监听逻辑
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2018/4/23 - 10:14
 * @since 1.0.0
 */
public interface TradeListener {


    /** 支付成功
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2018/4/23-10:14
     * @param outTradeNo
     * @param beforeCall
     * @return
     **/
    void onPayTradeSuccess(String outTradeNo, long beforeCall);

    /** 支付处理中
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2018/4/23-10:14
     * @param outTradeNo
     * @param beforeCall
     * @return
     **/
    void onPayInProgress(String outTradeNo, long beforeCall);

    /** 支付失败
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2018/4/23-10:14
     * @param outTradeNo
     * @param beforeCall
     * @return
     **/
    void onPayFailed(String outTradeNo, long beforeCall);

    /** 建立连接异常
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2018/4/23-10:14
     * @param outTradeNo
     * @param beforeCall
     * @return
     **/
    void onConnectException(String outTradeNo, long beforeCall);

    /** 报文上送异常
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2018/4/23-10:14
     * @param outTradeNo
     * @param beforeCall
     * @return
     **/
    void onSendException(String outTradeNo, long beforeCall);

    /** 报文接收异常
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2018/4/23-10:14
     * @param outTradeNo
     * @param beforeCall
     * @return
     **/
    void onReceiveException(String outTradeNo, long beforeCall);
}
