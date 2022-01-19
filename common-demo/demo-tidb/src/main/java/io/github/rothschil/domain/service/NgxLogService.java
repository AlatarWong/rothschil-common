package io.github.rothschil.domain.service;


import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.domain.entity.NgxLog;
import io.github.rothschil.domain.mapper.NgxLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 16:11
 * @since 1.0.0
 */
@Slf4j
@Service(value = "ngxLogService")
public class NgxLogService extends BaseService<NgxLogMapper,NgxLog, Long> {


    public int insertBatchByOn(List<NgxLog> locations){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
        long startTime = System.currentTimeMillis();
        int result =0;
        try {
            result = baseMpper.insertBatchByOn(locations);
        } catch (TransactionException e) {
            e.printStackTrace();
        } finally {
            transactionManager.commit(transactionStatus);
        }
        long endTime = System.currentTimeMillis();
        log.warn(" 耗时 {} ms",(endTime - startTime));
        return result;
    }

    public SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public DataSourceTransactionManager transactionManager;

    @Autowired
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
