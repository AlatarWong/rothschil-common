 package io.github.rothschil.domain.repository;

 import org.apache.ibatis.annotations.Mapper;
 import org.apache.ibatis.annotations.Param;
 import org.apache.ibatis.annotations.Select;


 public interface IntfRepository<Intf> extends BaseRepository<Intf, Long>,JpaSpecificationExecutor<Intf>{

     @Select("select * from intf_log where caller=#caller")
     Intf findByCaller(@Param("caller") String caller);
 }
