package xyz.wongs.drunkard.moon.service;//package xyz.wongs.drunkard.war3.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.drunkard.base.persistence.mybatis.mapper.BaseMapper;
import xyz.wongs.drunkard.base.persistence.mybatis.service.BaseService;
import xyz.wongs.drunkard.moon.entity.ImageInfo;
import xyz.wongs.drunkard.moon.mapper.ImageInfoMapper;

import java.util.List;

/**
 * @Description
 * @author WCNGS@QQ.COM
 *
 * @date 2020/9/9 16:11
 * @Version 1.0.0
*/
@Service(value= "imageInfoService")
@Transactional(readOnly = true)
public class ImageInfoService extends BaseService<ImageInfo, Long> {

    @Autowired
    private ImageInfoMapper imageInfoMapper;

    @Override
    protected BaseMapper<ImageInfo, Long> getMapper() {
        return imageInfoMapper;
    }

	@Transactional(rollbackFor = Exception.class)
	public void insert(List<ImageInfo> lists){
        imageInfoMapper.batchInsert(lists);
	}


}
