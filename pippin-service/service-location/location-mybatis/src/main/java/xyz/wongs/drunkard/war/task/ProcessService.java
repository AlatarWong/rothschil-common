package xyz.wongs.drunkard.war.task;

import xyz.wongs.drunkard.war.domain.entity.Location;

import java.util.List;

/**
 * @author WCNGS@QQ.COM
 * @date 2020/9/9 15:14
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface ProcessService {


    /**
     * 初始化省、直辖区、自治区
     *
     * @param url URL
     * @return boolean
     * @date 2020/9/9 15:16
     */
    boolean intiRootUrl(String url);

    /**
     * 初始化省会城市等
     *
     * @param url            URL
     * @param parentLocation 上级Location
     * @date 2020/9/9 15:16
     */
    void initLevelOne(String url, Location parentLocation);

    /**
     * 解析并初始化区县
     *
     * @param url      URL
     * @param location Location
     * @return boolean
     * @date 2020/9/5 10:24
     */
    boolean initLevelTwo(String url, Location location);

    /**
     * 解析并初始化区县
     *
     * @param url      URL
     * @param location Location
     * @param flag     flag
     * @date 2020/9/5 10:24
     */
    void initLevelThrid(String url, Location location, String flag);

    /**
     * 解析并初始化乡\镇\街道
     *
     * @param url      URL
     * @param location Location
     * @date 2020/9/5 10:24
     */
    void initLevelThrid(String url, Location location);

    /**
     * 解析并初始化 自然村\社区
     *
     * @param url             URL
     * @param thridLevelLocas 上级Location列表
     * @date 2020/9/5 10:24
     */
    void initLevelFour(String url, List<Location> thridLevelLocas);

}
