package xyz.wongs.drunkard.alipay.model.hb;

/** 交易结构体接口，用于隐藏系统商交易结构体和机具商交易结构体的不同
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2021/9/23 - 10:06
 * @since 1.0.0
 */
public interface TradeInfo {

    /** 获取交易状态
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-10:07
     * @param
     * @return HbStatus
     **/
    HbStatus getStatus();


    /** 获取交易时间
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @description //TODO
     * @date 2021/9/23-10:07
     * @param
     * @return double
     **/
    double getTimeConsume();
}
