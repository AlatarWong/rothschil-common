package xyz.wongs.drunkard.alipay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wongs.drunkard.alipay.model.ExtendParams;
import xyz.wongs.drunkard.alipay.model.GoodsDetail;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import xyz.wongs.drunkard.alipay.pojo.AlipayProperty;
import xyz.wongs.drunkard.alipay.pojo.OssBed;
import xyz.wongs.drunkard.alipay.pojo.form.Payment;
import xyz.wongs.drunkard.base.utils.DateUtils;
import xyz.wongs.drunkard.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private OssBed ossBed;

    @Autowired
    private AlipayProperty alipayProperty;


    public String pay(Payment payment){
        AlipayTradePrecreateRequestBuilder builder = createRequestBuilder(payment);

        return null;
    }

    private AlipayTradePrecreateRequestBuilder createRequestBuilder(Payment payment) {
        String totalAmount =payment.getTotalAmount()+"";
        String subject = new StringBuilder().append("[Alipay scan payment],Order: ").append(payment.getOutTradeNo()).toString();
        String unAmount = payment.getUnAmount()+"";
        String body = new StringBuilder().append("[Order]:").append(payment.getOutTradeNo()).append(",[Total price]:").append(totalAmount).toString();
        String sellerId = DateUtils.getTransId();
        String operatorId = sellerId;
        String storeId = StringUtils.getUuid();
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(alipayProperty.getProviderId());

        return new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(payment.getOutTradeNo())
                .setUndiscountableAmount(unAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(alipayProperty.getTimeoutExpress())
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl(ossBed.getAlipayCallbackUrl())
                .setGoodsDetailList(details(payment));
    }

    private List<GoodsDetail> details(Payment payment){
        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        goodsDetailList.add(GoodsDetail.newInstance(DateUtils.getTransId(), payment.getSubject(), StringUtils.toPrice(payment.getTotalAmount(),payment.getUnAmount()), 1));
        return goodsDetailList;
    }

}
