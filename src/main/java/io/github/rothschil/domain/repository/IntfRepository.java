 package io.github.rothschil.domain.repository;


 import io.github.rothschil.common.base.persistence.repository.BaseRepository;
 import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

 public interface IntfRepository<Intf> extends BaseRepository<Intf, Long>, JpaSpecificationExecutor<Intf> {


 }
