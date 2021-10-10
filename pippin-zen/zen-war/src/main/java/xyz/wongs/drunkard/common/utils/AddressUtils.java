package xyz.wongs.drunkard.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.wongs.drunkard.base.constant.Constants;
import xyz.wongs.drunkard.base.utils.HttpUtils;
import xyz.wongs.drunkard.base.utils.IpUtils;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.drunkard.common.conf.DrunkardConfig;

/**
 * 获取地址类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 21:38
 * @since 1.0.0
 */
public class AddressUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AddressUtils.class);

    /**
     * IP地址查询
     */
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 未知地址
     */
    public static final String UNKNOWN = "XX XX";

    /**
     * 查询开源网址获取真实的地理位置信息
     *
     * @param ip ip地址
     * @return String
     * @date 20/12/9 17:32
     */
    @Deprecated
    public static String getRealAddressByIp(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (DrunkardConfig.isAddressEnabled()) {
            try {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr)) {
                    LOG.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                LOG.error("获取地理位置异常 {}", e);
            }
        }
        return UNKNOWN;
    }


    /**
     * 查询本地的IP库获取真实的地理位置信息
     *
     * @param ip 源IP地址
     * @return String
     * @date 20/12/9 17:32
     */
    public static String getRealAddressByLocalIP(String ip) {
        return ip;
    }
}
