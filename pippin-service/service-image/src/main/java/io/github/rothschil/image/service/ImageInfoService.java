package io.github.rothschil.image.service;//package io.github.rothschil.war3.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.image.entity.ImageInfo;
import io.github.rothschil.image.mapper.ImageInfoMapper;

import java.util.List;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 16:11
 * @since 1.0.0
 */
@Service(value = "imageInfoService")
@Transactional(readOnly = true)
public class ImageInfoService extends BaseService<ImageInfo, Long> {

    private ImageInfoMapper imageInfoMapper;

    @Autowired
    public void setImageInfoMapper(ImageInfoMapper imageInfoMapper) {
        this.imageInfoMapper = imageInfoMapper;
    }

    @Override
    protected BaseMapper<ImageInfo, Long> getMapper() {
        return imageInfoMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(List<ImageInfo> lists) {
        imageInfoMapper.batchInsert(lists);
    }


}
