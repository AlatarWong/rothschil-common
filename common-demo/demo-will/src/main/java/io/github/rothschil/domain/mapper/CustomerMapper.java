package io.github.rothschil.domain.mapper;


import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.safe.Encrypt;
import io.github.rothschil.domain.entity.Customer;
import org.apache.ibatis.annotations.Param;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 15:12
 * @since 1.0.0
 */
public interface CustomerMapper extends BaseMapper<Customer, Long> {

    int addCustomer(@Param("phone") Encrypt phone, @Param("address") String address);

    Customer findCustomer(@Param("phone") Encrypt phone);

}