package xyz.wongs.drunkard.alipay.service;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wongs.drunkard.alipay.model.ExtendParams;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FPrecreateResult;
import xyz.wongs.drunkard.oss.util.OssUploadUtil;
import xyz.wongs.drunkard.alipay.pojo.AlipayProperty;
import xyz.wongs.drunkard.alipay.pojo.GoodsDetail;
import xyz.wongs.drunkard.oss.bo.OssBed;
import xyz.wongs.drunkard.alipay.pojo.form.OrderInfo;
import xyz.wongs.drunkard.alipay.service.impl.AlipayTradeServiceImpl;
import xyz.wongs.drunkard.alipay.util.ZxingUtils;
import xyz.wongs.drunkard.base.utils.DateUtils;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.drunkard.base.utils.file.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * 
 * @date 2021/9/26 - 11:08
 * @since 1.0.0
 */
public abstract class AbstractPay {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPay.class);

    @Autowired
    private OssBed ossBed;

    @Autowired
    private AlipayProperty alipayProperty;

    protected String pretreatment(Long orderNo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOutTradeNo(orderNo+"");
        orderInfo.setSubject(StringUtils.getRandomString(4));
        orderInfo.setTotalAmount(23424);
        return pretreatment(orderInfo);
    }


    protected String pretreatment(OrderInfo orderInfo) {
        String orderNo = orderInfo.getOutTradeNo();
        AlipayTradePrecreateRequestBuilder builder = createRequestBuilder(orderInfo);
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        // 支付交互响应
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                String remote = uploadOss(response);
                logger.info("Alipay Pre-order Success, The QR code URL is {}", remote);
                return remote;
            case FAILED:
                logger.error("Alipay Pre-order Failed, [Order No] {}", orderNo);
                break;
            case UNKNOWN:
                logger.error("System Error {}", orderNo);
                break;
            default:
                logger.error("Does not support transactions {}", orderNo);
                break;
        }
        return null;
    }

    protected AlipayTradePrecreateRequestBuilder createRequestBuilder(OrderInfo orderInfo) {
        String totalAmount = orderInfo.getTotalAmount() + "";
        String subject = "订单号" + orderInfo.getOutTradeNo();
        String unAmount = orderInfo.getUnAmount() + "";
        String body = "订单号" + orderInfo.getOutTradeNo() + ",[Total price]:" + totalAmount;
        String sellerId = "";
        String operatorId = "test_operator_id";
        String storeId = "test_store_id";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(alipayProperty.getProviderId());

        return new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(orderInfo.getOutTradeNo())
                .setUndiscountableAmount(unAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(alipayProperty.getTimeoutExpress())
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl(alipayProperty.getCallback())
                .setGoodsDetailList(details(orderInfo));
    }



    /** 根据订单中商品列表 拼装商品支付明细
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-10:59
     * @param orderInfo 订单
     * @return GoodsDetail>
     **/
    protected List<GoodsDetail> details(OrderInfo orderInfo) {
        List<String> alipayGoodsIds = orderInfo.getAlipayGoodsIds();
        List<GoodsDetail> goodsDetailList = new ArrayList<>(alipayGoodsIds.size());
        String transId = DateUtils.getTransId();
        GoodsDetail detail = xyz.wongs.drunkard.alipay.pojo.GoodsDetail.newInstance(transId,"goods_id001",1000,1);
        goodsDetailList.add(detail);
        return goodsDetailList;
    }

    /**
     * 生成二维码上传远程OSS图床
     *
     * @param response 响应实体
     * @return String 远程图床的路径
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-15:57
     **/
    protected String uploadOss(AlipayTradePrecreateResponse response) {
        String qrPath = String.format("/qr-%s.png", response.getOutTradeNo());
        String foldPath = ossBed.getDirectory() + qrPath;
        FileUtil.existsFolder(foldPath);
        File folder = new File(foldPath);
        ZxingUtils.getQRCodeImge(response.getQrCode(), 256, folder.getPath());
        // 将生成的二维码上传到阿里云OSS，然后将二维码url地址返回给前端展示
        return OssUploadUtil.upload(ossBed, folder.getPath());
    }

    /**
     * 打印应答
     * @param response 响应实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-14:54
     **/
    protected void dumpResponse(AlipayResponse response) {

        if (response != null) {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("[AlipayResponse Body] {}", response.getBody());
        }
    }

    /** 回调
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-14:54
     * @param params 订单等参数
     * @return boolean
     **/
//    public boolean aliCallback(Map<String, String> params) {
//        Long orderNo = Long.parseLong(params.get("out_trade_no"));
//        String tradeNo = params.get("trade_no");
//        String tradeStatus = params.get("trade_status");
//        logger.info("============支付宝回调日志：============ orderNo: {}, tradeNo: {}, tradeStatus: {}.", orderNo, tradeNo, tradeStatus);
//        // 查询数据库订单
//        // 这个自定义的状态，主要用于下面测试
//        Order dbOrder = new Order();
//        dbOrder.setStatus(10);
//        // 如果>=已付款状态的，都表示重复回调了
//        if (dbOrder.getStatus() >= 20) {
//            return false;
//        }
//        // 校验通过，则更新订单时间和支付状态
//        if (PayConst.TRADE_SUCCESS.equalsIgnoreCase(tradeStatus)) {
//            dbOrder.setStatus(20);
//        }
//        return true;
//    }
}
