package io.github.rothschil.domain.service;

import io.github.rothschil.common.base.persistence.service.BaseService;
import io.github.rothschil.common.exception.NullServiceException;
import io.github.rothschil.domain.entity.Intf;
import io.github.rothschil.domain.entity.TblCdmaHlr;
import io.github.rothschil.domain.repository.IntfRepository;
import io.github.rothschil.domain.repository.TblCdmaHlrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = NullServiceException.class)
public class TblCdmaHlrService extends BaseService<TblCdmaHlrRepository, TblCdmaHlr, Long> {


}