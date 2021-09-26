package xyz.wongs.drunkard.alipay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.wongs.drunkard.alipay.pojo.AlipayProperty;
import xyz.wongs.drunkard.alipay.pojo.OssBed;
import xyz.wongs.drunkard.alipay.pojo.form.OrderInfo;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/25 - 20:49
 * @version 1.0.0
 */
@Service
public class PaymentService extends AbstractPay{

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private OssBed ossBed;

    @Autowired
    private AlipayProperty alipayProperty;


    public String pay(OrderInfo orderInfo){
        return pretreatment(orderInfo);
    }



}
