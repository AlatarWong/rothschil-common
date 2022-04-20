package io.github.rothschil.domain.web;

import io.github.rothschil.base.persistence.mybatis.safe.Encrypt;
import io.github.rothschil.domain.entity.Customer;
import io.github.rothschil.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {


    private CustomerService customerService;

    @GetMapping("addCustomer")
    public String addCustomer(@RequestParam("phone") String phone, @RequestParam("address") String address) {
        int result = customerService.addCustomer(new Encrypt(phone), address);
        return "添加结果: " + result;
    }

    @GetMapping("findCustomer")
    public Customer findCustomer(@RequestParam("phone") String phone) {
        return customerService.findCustomer(new Encrypt(phone));
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
