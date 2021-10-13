package xyz.wongs.drunkard.moon.mapper;


import xyz.wongs.drunkard.base.persistence.mybatis.mapper.BaseMapper;
import xyz.wongs.drunkard.moon.entity.ImageInfo;

import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/13 - 9:41
 * @since 1.0.0
 */
public interface ImageInfoMapper extends BaseMapper<ImageInfo, Long> {

    /** 根据主键删除
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-14:51
     * @param id    主键
     * @return int
     **/
    @Override
    int deleteByPrimaryKey(Long id);

    /** 根据主键查询
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-14:51
     * @param id    主键
     * @return ImageInfo
     **/
    @Override
    ImageInfo selectByPrimaryKey(Long id);

    /** 根据实例更新
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-14:51
     * @param record    实例
     * @return int
     **/
    @Override
    int updateByPrimaryKeySelective(ImageInfo record);

    /** 根据主键更新
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-14:51
     * @param record    实例
     * @return int
     **/
    @Override
    int updateByPrimaryKey(ImageInfo record);

    /** 批量插入
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-14:51
     * @param lists    列表
     **/
    void batchInsert(List<ImageInfo> lists);

}