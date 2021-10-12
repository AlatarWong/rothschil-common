package xyz.wongs.drunkard.alipay.model.result;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import xyz.wongs.drunkard.alipay.model.TradeStatus;

/** 当面付2.0预下单（产生二维码）应答
 * @author <a href="https://github.com/rothschil">Sam</a>
 *
 * @date 2018/4/23 - 10:08
 * @since 1.0.0
 */
public class AlipayF2FPrecreateResult implements Result {

    private TradeStatus tradeStatus;
    private AlipayTradePrecreateResponse response;

    public AlipayF2FPrecreateResult(AlipayTradePrecreateResponse response) {
        this.response = response;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public void setResponse(AlipayTradePrecreateResponse response) {
        this.response = response;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public AlipayTradePrecreateResponse getResponse() {
        return response;
    }

    @Override
    public boolean isTradeSuccess() {
        return response != null &&
                TradeStatus.SUCCESS.equals(tradeStatus);
    }
}
