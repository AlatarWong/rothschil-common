package xyz.wongs.drunkard.alipay.model.result;

import com.alipay.api.response.AlipayTradeRefundResponse;
import xyz.wongs.drunkard.alipay.model.TradeStatus;

/** 当面付2.0退货应答
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2021/9/23 - 10:09
 * @version 1.0.0
 */
public class AlipayF2FRefundResult implements Result {
    private TradeStatus tradeStatus;
    private AlipayTradeRefundResponse response;

    public AlipayF2FRefundResult(AlipayTradeRefundResponse response) {
        this.response = response;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public void setResponse(AlipayTradeRefundResponse response) {
        this.response = response;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public AlipayTradeRefundResponse getResponse() {
        return response;
    }

    @Override
    public boolean isTradeSuccess() {
        return response != null &&
                TradeStatus.SUCCESS.equals(tradeStatus);
    }
}
