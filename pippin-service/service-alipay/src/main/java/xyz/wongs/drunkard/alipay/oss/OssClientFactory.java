package xyz.wongs.drunkard.alipay.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import xyz.wongs.drunkard.alipay.config.Constants;

import java.util.Map;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/22 - 15:09
 * @version 1.0.0
 */
public enum OssClientFactory {

    /**
     * 枚举元素本身就是单例
     */
    INSTANCE;

    private static OSS CLIENT;


    /** 获取单例
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-10:01
     * @param aliossMap
     * @return OSS
     **/
    public OSS singletonInstance(Map<String, String> aliossMap){

        if(null==CLIENT){
            CLIENT = new OSSClientBuilder().build(aliossMap.get(Constants.OSS_ENDPOINT), aliossMap.get(Constants.OSS_ACCESS_KEY_ID), aliossMap.get(Constants.OSS_ACCESS_KEY_SECRET));
        }
        return CLIENT;
    }

    /** 关闭连接
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @date 2021/9/23-10:01
     * @return void
     **/
    public void shutDown(){
        if(null==CLIENT){
            CLIENT.shutdown();
        }
    }
}
