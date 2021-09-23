package xyz.wongs.drunkard.alipay.service;


import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.wongs.drunkard.alipay.config.Configs;
import xyz.wongs.drunkard.alipay.config.Constants;
import xyz.wongs.drunkard.alipay.model.ExtendParams;
import xyz.wongs.drunkard.alipay.model.GoodsDetail;
import xyz.wongs.drunkard.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import xyz.wongs.drunkard.alipay.model.result.AlipayF2FPrecreateResult;
import xyz.wongs.drunkard.alipay.oss.OssUpload;
import xyz.wongs.drunkard.alipay.pojo.Order;
import xyz.wongs.drunkard.alipay.service.impl.AlipayTradeServiceImpl;
import xyz.wongs.drunkard.alipay.util.FileUtil;
import xyz.wongs.drunkard.alipay.util.ZxingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 14:51
 * @version 1.0.0
 */
@Service
public class AliPayService {

    private static final Logger logger = LoggerFactory.getLogger(AliPayService.class);

    @Value("${alipay.callback.url}")
    private String alipayCallbackUrl;

    @Autowired
    @Qualifier("aliossMap")
    protected Map<String, String> aliossMap;

    /** 支付宝生成二维码
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-15:59
     * @param orderNo   订单id
     * @return String 远程图片访问地址
     **/
    public String alipay(Long orderNo) {

        AlipayTradePrecreateRequestBuilder builder = createRequestBuilder(orderNo);
        Configs.init("zfbinfo.properties");
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                String remote  = uploadOss(response);
                logger.info("Alipay Pre-order Success qrPath {}" , remote);
                return remote;
            case FAILED:
                logger.error("Alipay Pre-order Failed {}",orderNo);
                break;
            case UNKNOWN:
                logger.error("System Error {}",orderNo);
                break;
            default:
                logger.error("Does not support transactions {}",orderNo);
                break;
        }
        return null;
    }

    /** 生成二维码上传远程OSS图床
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-15:57
     * @param response 响应实体
     * @return String 远程图床的路径
     **/
    private String uploadOss(AlipayTradePrecreateResponse response){
        String qrPath = String.format("/qr-%s.png", response.getOutTradeNo());
        String foldPath = aliossMap.get(Constants.OSS_TEMP_DIRECTORY)+qrPath;
        FileUtil.existsFolder(foldPath);
        File  folder = new File(foldPath);
        ZxingUtils.getQRCodeImge(response.getQrCode(), 256, folder.getPath());
        ZxingUtils.getQRCodeImge(response.getQrCode(), 256, folder.getPath());
        // 将生成的二维码上传到阿里云OSS，然后将二维码url地址返回给前端展示
        return OssUpload.upload(aliossMap,folder.getPath());
    }

    /** 打印应答
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-14:54
     * @param response 响应实体
     * @return void
     **/
    private void dumpResponse(AlipayResponse response) {

        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    /** 回调
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-14:54
     * @param params
     * @return boolean
     **/
    public boolean aliCallback(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        logger.info("============支付宝回调日志：============ orderNo: {}, tradeNo: {}, tradeStatus: {}.", orderNo, tradeNo, tradeStatus);
        // 查询数据库订单
        // Order order = orderMapper.selectByOrderNo(orderNo);
        // 这个自定义的状态，主要用于下面测试
        Order dbOrder = new Order();
        dbOrder.setStatus(10);
        // 如果订单为空，忽略
        if (dbOrder == null) {
            // return ServerResponse.createByErrorMessage("非快乐慕商城的订单,回调忽略");
            return false;
        }
        // 如果>=已付款状态的，都表示重复回调了
        if (dbOrder.getStatus() >= 20) {
            //return ServerResponse.createBySuccess("支付宝重复调用");
            return false;
        }
        // 校验通过，则更新订单时间和支付状态
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            //dbOrder.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            dbOrder.setStatus(20);
            //orderMapper.updateByPrimaryKeySelective(order);
        }
        // 插入订单日志
        /*PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);
        payInfoMapper.insert(payInfo);*/

        return true;
    }

    private List<GoodsDetail> queryGoodsDetail(){
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        goodsDetailList.add(goods1);
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);
        return goodsDetailList;
    }

    /** 创建扫码支付请求builder，设置请求参数
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-15:39
     * @param orderNo 订单编号
     * @return AlipayTradePrecreateRequestBuilder
     **/
    private AlipayTradePrecreateRequestBuilder createRequestBuilder(Long orderNo){
        Map<String, String> resultMap = new HashMap<>();

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(orderNo);

        // 根据userId和orderNo到数据库验证订单是否存在
        resultMap.put("orderNo", outTradeNo);

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
        List<GoodsDetail> goodsDetailList = queryGoodsDetail();

        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl(alipayCallbackUrl)
                .setGoodsDetailList(goodsDetailList);
                return builder;
    }
}