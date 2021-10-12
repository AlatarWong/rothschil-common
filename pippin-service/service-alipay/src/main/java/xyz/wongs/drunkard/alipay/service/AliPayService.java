package xyz.wongs.drunkard.alipay.service;


import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wongs.drunkard.alipay.model.ExtendParams;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FPrecreateResult;
import xyz.wongs.drunkard.alipay.pojo.AlipayProperty;
import xyz.wongs.drunkard.alipay.pojo.GoodsDetail;
import xyz.wongs.drunkard.alipay.pojo.form.OrderInfo;
import xyz.wongs.drunkard.alipay.service.impl.AlipayTradeServiceImpl;
import xyz.wongs.drunkard.base.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/23 - 14:51
 * @since 1.0.0
 */
@Service
public class AliPayService extends AbstractPay {

    private static final Logger logger = LoggerFactory.getLogger(AliPayService.class);

    @Autowired
    private AlipayProperty alipayProperty;

    public String alipay(Long orderNo) {
        Map<String, String> resultMap = new HashMap<>();
        // 根据userId和orderNo到数据库查询订单是否存在。。。
        resultMap.put("orderNo", String.valueOf(orderNo));
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(orderNo);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("支付宝扫码支付,订单号:").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "0.01";

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        String transId = DateUtils.getTransId();
        GoodsDetail detail = GoodsDetail.newInstance(transId, "goods_id001", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(detail);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(alipayProperty.getCallback())
                .setGoodsDetailList(goodsDetailList);
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                String remote = "uploadOss(response)";
                logger.info("Alipay Pre-order Success, qrPath={}", remote);
                return remote;
            case FAILED:
                logger.error("支付宝预下单失败!!!");
                break;
            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                break;
            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return null;
    }

    public String pay(OrderInfo orderInfo) {
        return pretreatment(orderInfo);
    }

}