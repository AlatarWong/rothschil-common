package xyz.wongs.drunkard.alipay.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.wongs.drunkard.alipay.pojo.form.Payment;
import xyz.wongs.drunkard.alipay.service.PaymentService;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 12:29
 * @version 1.0.0
 */
@Controller
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay-ment")
    public String payment(@ModelAttribute Payment payment) {
        LOG.info(payment.toString());



        return "index";
    }


}
