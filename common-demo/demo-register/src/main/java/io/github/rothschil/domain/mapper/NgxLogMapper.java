package io.github.rothschil.domain.mapper;


import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.domain.entity.NgxLog;

import java.util.List;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 15:12
 * @since 1.0.0
 */
public interface NgxLogMapper extends BaseMapper<NgxLog, Long> {

    /**
     * 批量插入
     *
     * @param locations Location列表
     * @return int
     * @date 2020/9/9 15:12
     */
    int insertBatchByOn(List<NgxLog> locations);


}