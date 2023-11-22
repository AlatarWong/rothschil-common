package io.github.rothschil.common.constant;

import org.springframework.stereotype.Controller;

/**
 * 通用常量信息
 */
public class Constants {

    public static final String TRANS_ID ="UNKNOW";

    /**
     * IP网卡信息，主环境
     */
    public static final String IPADDRESS_MASTR = "80";

    /**
     * IP网卡信息，备用环境
     */
    public static final String IPADDRESS_SLAVE = "70";

    public static final String BSS_APP_KEY= "JS00000009";


    public static final String SETNX_PREFIX = "SETNX_PREFIX:";

    public static final String POINT = ".";

    public static final String HTTP_STATUS_200 = "200";
    public static final String NORMAL = "1000";


    public static final String SERV_SCE = "SCE";
    public static final String SERV_INTF = "INTF";

    /**
     * Linux文件串
     */
    public static final String LINUX_SEPARATOR = "/";
    public static final String BRACKET_LEFT ="[";
    public static final String BRACKET_RIGHT ="]";
    public static final String COMMA =",";
    public static final String ZQ ="13";
    public static final String OLD ="65";
    /**
     * Windows文件串
     */
    public static final String Windows_SEPARATOR = "\\";

    /**
     * caret 特殊符号
     */
    public static final String CARET = "^";

    /**
     * UTF-8 字符集
     */
    public static final String CHAR_X = "X";

    /**
     * UTF-8 字符集
     */
    public static final String WILDCARD = "*";
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    public static final String INTF_CONF_CACHE = "INTF_CONF";

    //正常状态
    public static final String STATUS_E = "E";

    /**
     * 10000号
     */
    public static final String N_10000 = "10000";

    public static final String N_00000 = "0000";

    /**
     * 10001号
     */
    public static final String N_10001 = "10001";
    /**
     * 10009号
     */
    public static final String N_10009 = "10009";

    /**
     * 1183131号
     */
    public static final String N_1000111 = "1000111";

    public static final String N_1000112 = "1000112";

    /**
     * 96180号
     */
    public static final String N_1000113 = "1000113";

    //被叫属性10000
    public static final Integer CALLEE_10000 = 0;

    //被叫属性10001
    public static final Integer CALLEE_10001 = 1;

    //被叫属性其他
    public static final Integer CALLEE_OTHER = 2;

    //【自研】携号转网标识能力接口协议.docx/IVR渠道
    public static final String CHANNEL_IVR = "10006";

    /**
     * 成功标识 0
     */
    public static final String SUCCESS_0 = "0";

    /**
     * 失败标识 1
     */
    public static final String FAIL_1 = "1";

    /**
     * 数据启用标识 1
     */
    public static final String ACTIVE_1 = "1";

    /**
     * 数字字符串0
     */
    public static final String NUM_0 = "0";

    /**
     * 数字字符串10
     */
    public static final String NUM_10 = "10";

    /**
     * 数字字符串11
     */
    public static final String NUM_11 = "11";

    /**
     * 数字字符串12
     */
    public static final String NUM_12 = "12";

    /**
     * 数字字符串70
     */
    public static final String NUM_70 = "70";

    /**
     * 夜间
     */
    public static final String NIGHT_TIME = "night";

    /**
     * 数字字符串1
     */
    public static final String NUM_1 = "1";

    /**
     * 数字字符串2
     */
    public static final String NUM_2 = "2";

    public static final String ON_WAY = "301100";

    /**
     * 数字字符串3
     */
    public static final String NUM_3 = "3";

    /**
     * 数字字符串4
     */
    public static final String NUM_4 = "4";

    /**
     * 数字字符串4
     */
    public static final String NUM_5 = "5";

    /**
     * 数字字符串4
     */
    public static final String NUM_6 = "6";


    /**
     * 空格 space
     */
    public static final String SPACE = " ";

    /**
     * 数字字符串4
     */
    public static final String NUM_7 = "7";

    /**
     * 数字字符串4
     */
    public static final String NUM_8 = "8";

    /**
     * 数字字符串9
     */
    public static final String NUM_9 = "9";

    /**
     * 省会城市区号
     */
    public static final String IVR_PROVINCIAL_CAPITAL = "025";

    /**
     * 省会城市区号
     */
    public static final String IVR_CT_10000 = "02510000";

    /**
     * ivr调用方编码
     */
    public static final String IVR_SRC_CODE = "28";

    /**
     *
     */
    public final static String AI_SC_KEY = "51";

    /**
     * 技能号转换
     */
    public final static String SUFFIX_SC = "sc";

    /**
     * 默认全省
     */
    public final static String ALL_PROVICE = "-1";

    public static final String REDIS_KEY_ROUTE_0_ASRSKILLCONFIG = "REDIS_KEY_ROUTE_0_ASRSKILLCONFIG:";
    public static final String REDIS_KEY_ROUTE_1_0_S1000NIGTHTNODECONFIG = "REDIS_KEY_ROUTE_1_0_S1000NIGTHTNODECONFIG:";
    public static final String REDIS_KEY_ROUTE_1_1_S1000ASRSKILLCONFIG = "REDIS_KEY_ROUTE_1_1_S1000ASRSKILLCONFIG:";
    public static final String REDIS_KEY_ROUTE_20_0_URGENTSERVICE = "REDIS_KEY_ROUTE_20_0_URGENTSERVICE:";
    public static final String REDIS_KEY_ROUTE_21_INCOMINGREDLIST = "REDIS_KEY_ROUTE_21_INCOMINGREDLIST:";
    public static final String REDIS_KEY_ROUTE_23_S1000H5KDXZGUIDECONFIG = "REDIS_KEY_ROUTE_23_S1000H5KDXZGUIDECONFIG:";
    public static final String REDIS_KEY_ROUTE_24_SCGETENGCONFENTIRY1 = "REDIS_KEY_ROUTE_24_SCGETENGCONFENTIRY1:";

    public static final String REDIS_KEY_ROUTE_NOC_TRANSFER = "REDIS_KEY_ROUTE_NOC_TRANSFER:";

    /**
     * 最大同步数据量
     */
    public static final Integer MAX_SYNC_DATA_SIZE = 10000;

    /**
     * 服务名称
     */
    public static final String SESSION_ATTR_NAME_SERVICE_NAME = "SERVICE_NAME";

    /**
     * 呼叫流水号
     */
    public static final String SESSION_ATTR_NAME_CALL_ID = "CALL_ID";

    /**
     * 主叫号码
     */
    public static final String SESSION_ATTR_NAME_CALLER = "CALLER";

    /**
     * 调用号码
     */
    public static final String SESSION_ATTR_NAME_CALL_NO = "CALL_NO";

    /**
     * 新客标识
     */
    public static final String NEW_ARRIVAL = "NEW_ARRIVAL";

    public static final String INTERFACE_NOT_CONFIGURED = "RPC接口未匹配数据库记录";

    public static final String IP_REGEX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    public static final String TURS_URL_ERR = "地址转换URL错误";

    public static final String CONVERSION_FAILURE = "转换失败";

    /**
     * 录音记录推送主题
     */
    public static final String IVR_RECORD_ORIGINAL_FILE = "IVR_RECORD_ORIGINAL_FILE";


    public static final String COMPANY_ID = "17";


    /**
     *  标识常量，用于在 {@link Controller} 层面中统一处理 返回值
     */
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    /**
     * 公众
     */
    public static final String CUSBRANDR_85 = "85";

    /**
     * 异网用户
     */
    public static final String CUSBRANDR_99 = "99";

    /**
     * 本地本网
     */
    public static final String CUSBRANDR_98 = "98";


    /**
     * 敏感客户-特殊号码
     */
    public static final String CUSBRANDR_67 = "67";

    /**
     * 星级客户
     */
    public static final String CUSBRANDR_75 = "75";

    /**
     * 黑名单 102
     */
    public static final String CUSBRANDR_102 = "102";

    /**
     * 红名单 101
     */
    public static final String CUSBRANDR_101 = "101";

    /**
     *
     */
    public static final String IVR = "ivr";

    public static final String ONE = "ONE";
    public static final String TWO = "TWO";
    public static final String THREE = "THREE";

    public static final String HTTP_ERR_MSG_DEFAULT = "请求或者响应异常";

}