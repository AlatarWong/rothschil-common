package xyz.wongs.drunkard.war.domain.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.wongs.drunkard.base.persistence.jpa.repository.BaseRepository;
import xyz.wongs.drunkard.war.domain.entity.Location;

import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/8 - 15:18
 * @since 1.0.0
 */
public interface LocationRepository extends BaseRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    /**
     * @param lv 层级
     * @return List<Location>
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-15:26
     **/
    List<Location> findByLv(int lv);

}