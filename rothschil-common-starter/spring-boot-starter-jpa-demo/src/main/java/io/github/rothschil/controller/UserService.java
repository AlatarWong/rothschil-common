package io.github.rothschil.controller;

import io.github.rothschil.base.persistence.jpa.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/15 - 16:41
 * @since 1.0.0
 */
@Transactional(readOnly = true)
@Service
public class UserService extends BaseService<UserRepository,User, Long> {


    @Transactional
    public User insert(User user){
        return baseRepository.save(user);
    }

    public User getByKey(Long id){
        return baseRepository.getUserById(id);
    }

    public List<?> listBySql(String sql) {
        return baseRepository.simpleListBySql(sql);
    }

}
