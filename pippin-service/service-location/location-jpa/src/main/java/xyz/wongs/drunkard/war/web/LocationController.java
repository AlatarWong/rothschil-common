package xyz.wongs.drunkard.war.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.wongs.drunkard.war.domain.entity.Location;
import xyz.wongs.drunkard.war.domain.service.LocationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WCNGS@QQ.COM
 * @date 20/11/18 11:04
 * @since 1.0.0
 */
@Validated
@RestController
@RequestMapping(value = "/area")
public class LocationController {

    private LocationService locationService;

    @Autowired
    @Qualifier("locationService")
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * @param lv 层级
     * @return Location
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:59
     **/
    @RequestMapping(value = "/{lv}", method = RequestMethod.GET)
    public List<Location> getLocationListByLevel(@PathVariable(value = "lv") Integer lv) {
        return locationService.getLocationListByLevel(lv);
    }

    /**
     * @return Map
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:59
     **/
    @GetMapping("/test")
    public Map<String, Object> test() {
        HashMap<String, Object> data = new HashMap<>(3);
        data.put("info", "测试成功");
        return data;
    }

    /**
     * @param userId 主键
     * @return Map
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/11/8-14:59
     **/
    @GetMapping("/valid")
    public Map<String, Object> testValidator(Integer userId) {
        HashMap<String, Object> data = new HashMap<>(3);
        data.put("info", "测试成功 [userId]=" + userId);
        return data;
    }


}

