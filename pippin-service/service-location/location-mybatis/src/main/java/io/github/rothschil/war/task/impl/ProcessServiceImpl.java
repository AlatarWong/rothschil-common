package io.github.rothschil.war.task.impl;

import io.github.rothschil.war.domain.entity.Location;
import io.github.rothschil.war.domain.service.LocationService;
import io.github.rothschil.war.util.AreaCodeStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import io.github.rothschil.war.task.ProcessService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017年7月28日 上午11:31:30  *
 * @since 1.0.0
 */
@Slf4j
@Service("processService")
public class ProcessServiceImpl implements ProcessService {


    LocationService locationService;

    @Autowired
    @Qualifier("locationService")
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void initLevelOne(String url, Location parentLocation) {
        List<Location> levelOne = null;
        try {
            levelOne = getLevelOneByRoot(url, parentLocation.getLocalCode());
        } catch (IOException e) {
            log.error(" IOException msg {}", e.getMessage());
        }
        save(levelOne);
    }

    @Override
    public boolean initLevelTwo(String url, Location location) {
        try {
            List<Location> secondLevelLocas = getLocationSecondLevel(url, location);
            save(secondLevelLocas);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 初始化省、直辖区、自治区
     *
     * @param url URL
     * @return void
     * @author WCNGS@QQ.COM
     * @date 2018/6/30 23:29
     */
    @Override
    public boolean intiRootUrl(String url) {
        try {
            List<Location> rootLocations = getLocationRoot(url, "0");
            save(rootLocations);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Location> getLocationRoot(String url, String pCode) {
        List<Location> locas = new ArrayList<>(50);
        try {
            Elements eleProv = getElementsByConnection(url, "provincetr");
            for (Element e : eleProv) {
                Elements eleHerf = e.getElementsByTag("td").select("a[href]");
                if (null == eleHerf || eleHerf.size() == 0) {
                    continue;
                }
                for (Element target : eleHerf) {
                    String urls = target.attributes().asList().get(0).getValue();
                    Location location = Location.builder().localCode("0").url(urls).lv(0).localName(target.text())
                            .localCode(urls.substring(0, 2)).build();
                    locas.add(location);
                }
            }
        } catch (IOException e) {
            log.error(" IOException pCode={}", pCode);
        }
        return locas;
    }

    /**
     * 方法实现说明
     *
     * @param url      URL
     * @param location Location
     * @author WCNGS@QQ.COM
     * @date 2018/7/1 9:50
     */
    @Override
    public void initLevelThrid(String url, Location location) {
        this.initLevelThrid(url, location, "Y");
    }


    /**
     * 方法实现说明
     *
     * @param url      URL
     * @param location 实例
     * @param flag     标识
     * @author WCNGS@QQ.COM
     * @date 2018/7/1 16:24
     */
    @Override
    public void initLevelThrid(String url, Location location, String flag) {
        try {
            if (StringUtils.hasLength(location.getUrl())) {
                return;
            }
            List<Location> thridLevelLocas = getLocation(url, new String[]{"towntr", "href"}, location.getLocalCode(), 3, flag);
            location.setFlag(flag);
            locationService.updateByPrimaryKey(location);
            save(thridLevelLocas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(List<Location> locations) {
        //结果为空，抛出异常
        if (null == locations || locations.isEmpty()) {
            log.error(" target saved is null!");
            return;
        }
        locationService.insertBatchByOn(locations);

    }


    @Override
    public void initLevelFour(String url, List<Location> thridLevelLocas) {
        for (Location le : thridLevelLocas) {
            List<Location> locations = new ArrayList<>(12);
            String suffix = url + AreaCodeStringUtils.getUrlStrByLocationCode(le.getLocalCode(), 3) + le.getUrl();
            try {
                Elements es = getElementsByConnection(suffix, "villagetr");
                Location tempLocation;
                for (Element e : es) {
                    tempLocation = new Location(e.child(0).text(), e.child(2).text(), le.getLocalCode(), null, 4);
                    locations.add(tempLocation);
                }
                le.setFlag("Y");
                locationService.updateByPrimaryKey(le);
                save(locations);
            } catch (IOException e) {
                log.error(" IOException code={},msg={},url={}", le.getLocalCode(), e.getMessage(), suffix);
                int times = AreaCodeStringUtils.getSecond(3);
                try {
                    TimeUnit.SECONDS.sleep(times);
                } catch (InterruptedException interruptedException) {
                    log.error("msg={} ", interruptedException.getMessage());
                }
            } catch (Exception e) {
                log.error("Exception code={},msg={}", le.getLocalCode(), e.getMessage());
            }
        }
    }


    /**
     * @param url      URL
     * @param location 实例
     * @return List<Location>
     */
    public List<Location> getLocationSecondLevel(String url, Location location) {
        List<Location> locations = null;
        try {
            locations = new ArrayList<>(90);
            //URL地址截取
            //标识位
            boolean flag = false;
            Elements es = getElementsByConnection(url, "countytr");
            if (null == es) {
                log.error(url + " 不能解析！");
                return null;
            }
            Location tempLocation;
            for (Element e : es) {
                //针对市辖区 这种无URL的做特殊处理
                if (!flag) {
                    tempLocation = new Location(e.child(0).text(), e.child(1).text(), location.getLocalCode(), null, 2);
                    locations.add(tempLocation);
                    //标识位置为TURE
                    flag = true;
                    continue;
                }
                es = e.getElementsByAttribute("href");
                if (es.size() == 0) {
                    tempLocation = new Location(e.child(0).text(), e.child(1).text(), location.getLocalCode(), "", 2);
                    locations.add(tempLocation);
                    continue;
                }
                List<Attribute> attrs = es.get(0).attributes().asList();
                tempLocation = new Location(es.get(0).text(), es.get(1).text(), location.getLocalCode(), attrs.get(0).getValue(), 2);
                locations.add(tempLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }


    /**
     * 1、获取第一级地市信息
     * 2、第二级区县信息
     *
     * @param url   URL
     * @param pCode 上级code
     * @return List<Location>
     */
    public List<Location> getLevelOneByRoot(String url, String pCode) throws IOException {
        List<Location> locations = new ArrayList<>(20);
        Elements eles = getElementsByConnection(url, "citytr");
        if (null == eles) {
            log.error(url + " 不能解析！");
            return null;
        }
        Location location;
        for (Element e : eles) {
            eles = e.getElementsByAttribute("href");
            List<Attribute> attrs = eles.get(0).attributes().asList();
            location = new Location(eles.get(0).text(), eles.get(1).text(), pCode, attrs.get(0).getValue(), 1);
            locations.add(location);
        }
        return locations;
    }


    public List<Location> getLocation(String url, String[] cssClazz, String parentCode, Integer lv, String flag) throws IOException {
        List<Location> locations = new ArrayList<>(20);
        Elements eles = getElementsByConnection(url, cssClazz[0]);
        if (null == eles) {
            log.error(url + " 不能解析！");
            return null;
        }
        Location location;
        for (Element e : eles) {
            eles = e.getElementsByAttribute(cssClazz[1]);
            List<Attribute> attrs = eles.get(0).attributes().asList();
            location = new Location(eles.get(0).text(), eles.get(1).text(), parentCode, attrs.get(0).getValue(), lv, flag);
            locations.add(location);
        }
        return locations;
    }


    /**
     * 方法实现说明
     *
     * @param url       URL
     * @param clazzName 实例类名字
     * @return org.jsoup.select.Elements
     * @author WCNGS@QQ.COM
     * @date 2018/7/2 11:28
     */
    public Elements getElementsByConnection(String url, String clazzName) throws IOException {

        try {
            //设置CookieSpecs.STANDARD的cookie解析模式，下面为源码，对应解析格式我给出了备注
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            RequestConfig config = RequestConfig.custom()
                    //.setProxy(proxy)
                    //设置连接超时 ✔
                    // 设置连接超时时间 10秒钟
                    .setConnectTimeout(10000)
                    // 设置读取超时时间10秒钟
                    .setSocketTimeout(10000)
                    .build();
            httpget.setConfig(config);
            // 执行get请求
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            // 获取返回实体
            String content = EntityUtils.toString(entity, "GBK");
            // ============================= 【Jsoup】 ====================================
            Document doc = Jsoup.parse(content);
            return doc.getElementsByClass(clazzName);
        } catch (ConnectTimeoutException e) {
            log.error(" ConnectTimeoutException URL={},clazzName={},errMsg={}", url, clazzName, e.getMessage());
        }

        return null;
    }

}
