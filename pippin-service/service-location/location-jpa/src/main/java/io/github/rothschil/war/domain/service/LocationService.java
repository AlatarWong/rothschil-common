package io.github.rothschil.war.domain.service;


import io.github.rothschil.war.domain.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.rothschil.base.persistence.jpa.service.BaseService;
import io.github.rothschil.war.domain.entity.Location;

import java.util.List;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 16:11
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Service(value = "locationService")
@Transactional(readOnly = true)
public class LocationService extends BaseService<Location, Long> {

    private LocationRepository locationRepository;

    @Autowired
    @Qualifier("locationRepository")
    @Override
    public void setJpaRepository(JpaRepository<Location, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
        this.locationRepository = (LocationRepository) jpaRepository;
    }

    public List<Location> getLocationListByLevel(int lv) {
        return locationRepository.findByLv(lv);
    }

    public Page<Location> getList(Location location, int pageNum, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return locationRepository.find(location, pageable);
    }

    /**
     * 根据SQL，查询结果，获取结果列表
     *
     * @param sql 原生SQL语句
     * @return List
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    public List<Location> listBySql(String sql) {
        return locationRepository.listBySql(sql);
    }

    /**
     * 根据 hql，查询结果，获取结果列表
     *
     * @param hql HQL语句
     * @return List
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:42
     **/
    public List<Location> listByHql(String hql) {
        return locationRepository.listByHql(hql);
    }
}
