package io.github.rothschil.domain.service;

import io.github.rothschil.common.base.persistence.service.BaseService;
import io.github.rothschil.common.exception.NullServiceException;
import io.github.rothschil.domain.entity.Intf;
import io.github.rothschil.domain.repository.IntfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = NullServiceException.class)
public class IntfService extends BaseService<Intf, Long> {

    private IntfRepository intfRepository;

    @Autowired
    @Qualifier("intfRepository")
    @Override
    public void setJpaRepository(JpaRepository<Intf, Long> jpaRepository) {
        // TODO Auto-generated method stub
        this.jpaRepository=jpaRepository;
        this.intfRepository=(IntfRepository)jpaRepository;
    }

}