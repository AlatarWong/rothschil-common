package io.github.rothschil.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.rothschil.demo.entity.AtSystem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统列表 Mapper 接口
 * </p>
 *
 * @author Bean
 * @since 2021-10-23
 */
@Mapper
@Repository
public interface AtSystemMapper extends BaseMapper<AtSystem> {

}
