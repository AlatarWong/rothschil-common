package io.github.rothschil.demo.service.impl;

import io.github.rothschil.demo.mapper.AtSystemMapper;
import io.github.rothschil.demo.service.AtSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统列表 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2021-10-23
 */
@Service
public class AtSystemServiceImpl implements AtSystemService {



    private AtSystemMapper atSystemMapper;

    @Autowired
    public void setAtSystemMapper(AtSystemMapper atSystemMapper) {
        this.atSystemMapper = atSystemMapper;
    }

    public void init(){

    }
}
