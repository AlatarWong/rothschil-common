package xyz.wongs.drunkard.alipay.model.result;

import com.alipay.api.response.AlipayTradePayResponse;
import xyz.wongs.drunkard.alipay.model.TradeStatus;

/** 当面付2.0支付应答
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 *
 * @date 2018/4/23 - 10:08
 * @since 1.0.0
 */
public class AlipayF2FPayResult implements Result {

    private TradeStatus tradeStatus;
    private AlipayTradePayResponse response;

    public AlipayF2FPayResult(AlipayTradePayResponse response) {
        this.response = response;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public void setResponse(AlipayTradePayResponse response) {
        this.response = response;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public AlipayTradePayResponse getResponse() {
        return response;
    }

    @Override
    public boolean isTradeSuccess() {
        return response != null &&
                TradeStatus.SUCCESS.equals(tradeStatus);
    }
}
