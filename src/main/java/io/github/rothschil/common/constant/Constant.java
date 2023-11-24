package io.github.rothschil.common.constant;

import org.springframework.stereotype.Controller;

/**
 * 通用常量信息
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 */
public interface Constant {

    String ORIG_CLIENT_IP="";

    String USER_AGENT="User-Agent";

    String HOST="Host";


    /**
     *  标识常量，用于在 {@link Controller} 层面中统一处理 返回值
     */
    String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    String HTTP_ERR_MSG_DEFAULT = "请求或者响应异常";

    String IP_REGEX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    String TURS_URL_ERR = "地址转换URL错误";


    /**
     * 数字字符串1
     */
    String NUM_1 = "1";

    /**
     * 数字字符串2
     */
    String NUM_2 = "2";

    String ON_WAY = "301100";

    /**
     * 数字字符串3
     */
    String NUM_3 = "3";

    /**
     * 数字字符串4
     */
    String NUM_4 = "4";

    /**
     * 数字字符串4
     */
    String NUM_5 = "5";

    /**
     * 数字字符串4
     */
    String NUM_6 = "6";


    /**
     * 空格 space
     */
    String SPACE = " ";

    /**
     * 数字字符串4
     */
    String NUM_7 = "7";

    /**
     * 数字字符串4
     */
    String NUM_8 = "8";

    /**
     * 数字字符串9
     */
    String NUM_9 = "9";

    String POINT = ".";

    /**
     * IP网卡信息，主环境
     */
    String IPADDRESS_MASTR = "80";

    /**
     * IP网卡信息，备用环境
     */
    String IPADDRESS_SLAVE = "70";
}
