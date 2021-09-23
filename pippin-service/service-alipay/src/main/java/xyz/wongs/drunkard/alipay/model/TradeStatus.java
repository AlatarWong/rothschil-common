package xyz.wongs.drunkard.alipay.model;


/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @description //TODO
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 10:21
 * @version 1.0.0
 */
public enum TradeStatus {
    /**
     * 业务交易明确成功，比如支付成功、退货成功
      */
    SUCCESS

    /**
     * 业务交易明确失败，比如支付明确失败、退货明确失败
     */
    ,FAILED

    /**
     * 业务交易状态未知，此时不清楚该业务是否成功或者失败，需要商户自行确认
     */
    ,UNKNOWN
}
