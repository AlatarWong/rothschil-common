//package io.github.rothschil.alipay.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import io.github.rothschil.alipay.pojo.AlipayProperty;
//import io.github.rothschil.oss.bo.OssBed;
//import io.github.rothschil.alipay.pojo.form.OrderInfo;
//
///**
// * @author <a href="https://github.com/rothschil">Sam</a>
// * @date 2018/4/25 - 20:49
// * @since 1.0.0
// */
//@Service
//public class PaymentService extends AbstractPay {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
//
//    @Autowired
//    private OssBed ossBed;
//
//    @Autowired
//    private AlipayProperty alipayProperty;
//
//
//    public String pay(OrderInfo orderInfo) {
//        return pretreatment(orderInfo);
//    }
//
//}
