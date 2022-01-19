package io.github.rothschil.domain.service;


import io.github.rothschil.base.persistence.mybatis.safe.Encrypt;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.domain.entity.Customer;
import io.github.rothschil.domain.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 16:11
 * @since 1.0.0
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(value = "ngxLogService")
public class CustomerService extends BaseService<CustomerMapper, Customer, Long> {

    public int addCustomer(Encrypt phone, String address){
        return baseMpper.addCustomer(phone,address);
    }

    public Customer findCustomer(Encrypt phone){
        return baseMpper.findCustomer(phone);
    }

}
