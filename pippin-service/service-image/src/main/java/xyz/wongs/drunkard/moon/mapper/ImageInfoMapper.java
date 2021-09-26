package xyz.wongs.drunkard.moon.mapper;


import xyz.wongs.drunkard.base.persistence.mybatis.mapper.BaseMapper;
import xyz.wongs.drunkard.moon.entity.ImageInfo;

import java.util.List;

/**
 * @Author <a href="https://github.com/rothschil">Sam</a>
 * @Description //TODO
 * 
 * @date 2021/7/13 - 9:41
 * @Version 1.0.0
 */
public interface ImageInfoMapper extends BaseMapper<ImageInfo,Long> {
    @Override
    int deleteByPrimaryKey(Long id);

    @Override
    ImageInfo selectByPrimaryKey(Long id);

    @Override
    int updateByPrimaryKeySelective(ImageInfo record);

    @Override
    int updateByPrimaryKey(ImageInfo record);

    void batchInsert(List<ImageInfo> lists);

}