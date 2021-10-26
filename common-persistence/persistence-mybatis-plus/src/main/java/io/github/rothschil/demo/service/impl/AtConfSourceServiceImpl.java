package io.github.rothschil.demo.service.impl;

import io.github.rothschil.demo.entity.AtConfSource;
import io.github.rothschil.demo.mapper.AtConfSourceMapper;
import io.github.rothschil.demo.service.AtConfSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2021-10-23
 */
@Service
public class AtConfSourceServiceImpl implements AtConfSourceService {


    AtConfSourceMapper atConfSourceMapper;

    @Autowired
    public void setAtConfSourceMapper(AtConfSourceMapper atConfSourceMapper) {
        this.atConfSourceMapper = atConfSourceMapper;
    }

    @Override
    public List<AtConfSource> getAll() {
        return atConfSourceMapper.selectList();
    }
}
