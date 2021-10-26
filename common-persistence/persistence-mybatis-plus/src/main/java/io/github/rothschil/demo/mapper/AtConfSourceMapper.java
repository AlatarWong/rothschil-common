package io.github.rothschil.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.rothschil.demo.entity.AtConfSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author Bean
 * @since 2021-10-23
 */
@Mapper
@Repository
public interface AtConfSourceMapper extends BaseMapper<AtConfSource> {

    @Results(id="confMap",value={
            @Result(property = "id",column = "conf_id"),
            @Result(property = "host",column = "host"),
            @Result(property = "port",column = "port"),
            @Result(property = "systemId",column = "system_id"),
            @Result(property = "atSystem",column = "system_id",one = @One(select = "io.github.rothschil.demo.mapper.AtSystemMapper.selectById"))
    })
    @Select("SELECT * FROM at_conf_source")
    List<AtConfSource> selectList();

    @Select("select * from at_conf_source where 1=1 and " +
            "${ew.sqlSegment}")
    @ResultMap(value = "confMap")
    List<AtConfSource> selectAtConfSources(@Param("ew") QueryWrapper<AtConfSource> wrapper);

}
