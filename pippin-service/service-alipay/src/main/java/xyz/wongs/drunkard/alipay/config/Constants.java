package xyz.wongs.drunkard.alipay.config;

/** 常量定义
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @description //TODO
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/22 - 11:04
 * @version 1.0.0
 */
public class Constants {

    private Constants() {
    }

    /**
     * 成功
     */
    public static final String SUCCESS = "10000";
    /**
     * 用户支付中
      */
    public static final String PAYING  = "10003";
    /**
     * 失败
      */

    public static final String FAILED  = "40004";
    /**
     * 系统异常
      */
    public static final String ERROR   = "20000";

    /**
     * .
     */
    public static final String POINT=".";

    /**
     * 斜杠
     */
    public static final String SLASH="/";

    /**
     * 双斜杠
     */
    public static final String DOUL_SLASH="//";

    /**
     * OSS 定义
     */
    public static final String OSS_ENDPOINT="endpoint";
    public static final String OSS_ACCESS_KEY_ID="accessKeyId";
    public static final String OSS_ACCESS_KEY_SECRET="accessKeySecret";
    public static final String OSS_BUCKET_NAME="bucketName";
    public static final String OSS_TEMP_DIRECTORY="tempDirectory";
}
