package xyz.wongs.drunkard.alipay.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 14:51
 * @version 1.0.0
 */
@Service
public class AliPayService extends AbstractPay{

    private static final Logger logger = LoggerFactory.getLogger(AliPayService.class);

    /** 支付宝生成二维码
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-15:59
     * @param orderNo   订单id
     * @return String 远程图片访问地址
     **/
    public String alipay(Long orderNo) {
        return pretreatment(orderNo);
    }


}