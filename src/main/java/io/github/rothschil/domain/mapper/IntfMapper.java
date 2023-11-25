package io.github.rothschil.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IntfMapper<Intf> {

    @Select("select * from intf_log where caller=#caller")
    Intf findByCaller(@Param("caller") String caller);
}
