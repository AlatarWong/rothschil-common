package xyz.wongs.drunkard.war.domain.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.drunkard.base.persistence.mybatis.mapper.BaseMapper;
import xyz.wongs.drunkard.base.persistence.mybatis.service.BaseService;
import xyz.wongs.drunkard.war.domain.entity.Location;
import xyz.wongs.drunkard.war.domain.repository.LocationMapper;

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

    public PageInfo<Location> getLocationsByLv(int lv, int pageNum, int pageSize) {
        PageInfo<Location> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            Location location = new Location();
            location.setLv(lv);
            List<Location> locations = locationMapper.getList2(location);
            pageInfo = new PageInfo<>(locations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    public PageInfo<Location> getLocationsByLvAndFlag(int pageNum, int pageSize, Location location) {
        PageInfo<Location> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Location> locations = locationMapper.getList2(location);
            pageInfo = new PageInfo<>(locations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    public List<Location> getLocationBySupLocalCode(String supLocalCode) {
        // TODO Auto-generated method stub
        Location location = new Location();
        location.setSupLocalCode(supLocalCode);
        return locationMapper.getList(location);
    }

    public List<Location> getLocationByLvAndFlag(int lv, String flag) {
        Location location = Location.builder().lv(lv).flag(flag).build();
        return locationMapper.getList(location);
    }

    public List<Location> findLocationTreeNodeByLocalCode(String localCode) {
        return getLocationBySupLocalCode(localCode);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertBatchByOn(List<Location> locations) {
		locationMapper.insertBatchByOn(locations);
	}

    public List<Location> getLocationListByLevel(Integer lv) {
        Location location = new Location();
        location.setLv(lv);
        return locationMapper.getList(location);
    }

    public PageInfo<Location> getLocationsByLevel(Integer lv, int pageNumber) {
        //每页大小
        int pageSize = 20;
        return getLocationsByLv(lv, pageNumber, pageSize);
    }

    public List<Location> getLocationListById(Long id) {
        Location location = new Location();
        location.setId(id);
        return locationMapper.getList(location);
    }

    public List<Location> findLocationThrid(Integer lv, Integer rownum) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getLocationCountsByLevel(Integer lv) {
        //每页大小
        int pageSize = 20;
        Location location = new Location();
        location.setLv(lv);
        int size = locationMapper.getCount(location);
        if (size % pageSize != 0) {
            return (size / pageSize) + 1;
        } else {
            return size / pageSize;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateLocationFlag(String flag, Long id) {
        Location location = new Location();
        location.setId(id);
        location.setFlag(flag);
        locationMapper.updateByPrimaryKey(location);
    }

    private LocationMapper locationMapper;

	@Autowired
	public void setLocationMapper(LocationMapper locationMapper) {
		this.locationMapper = locationMapper;
	}

	@Override
    protected BaseMapper<Location, Long> getMapper() {
        return locationMapper;
    }

    public List<Location> getList2(Location location) {
        return locationMapper.getList2(location);
    }
}
