//package io.github.rothschil.alipay.web;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;
//import io.github.rothschil.alipay.pojo.form.OrderInfo;
//import io.github.rothschil.alipay.service.PaymentService;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author <a href="https://github.com/rothschil">Sam</a>
// * @date 2018/4/23 - 12:29
// * @since 1.0.0
// */
//@Controller
//public class PaymentController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping("/pay-ment")
//    public ModelAndView payment(@ModelAttribute OrderInfo orderInfo) {
//        String qrPath = paymentService.pay(orderInfo);
//        Map<String, String> repMap = new HashMap<String, String>(2);
//        repMap.put("qrPath", qrPath);
//        return new ModelAndView("pay", repMap);
//    }
//
//
//}
