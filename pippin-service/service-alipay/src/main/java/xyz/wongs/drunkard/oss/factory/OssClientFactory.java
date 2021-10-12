package xyz.wongs.drunkard.oss.factory;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import xyz.wongs.drunkard.oss.bo.OssBed;

/**
 * OSS Client工厂类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/22 - 15:09
 * @since 1.0.0
 */
public enum OssClientFactory {

    /**
     * 枚举元素本身就是单例
     */
    INSTANCE;

    private static OSS CLIENT;


    /**
     * 获取单例
     *
     * @param ossBed 图床信息
     * @return OSS OOS实例
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-10:01
     **/
    public OSS singletonInstance(OssBed ossBed) {
        if (null == CLIENT) {
            CLIENT = new OSSClientBuilder().build(ossBed.getEndpoint(), ossBed.getKey(), ossBed.getSecret());
        }
        return CLIENT;
    }

    /**
     * 关闭连接
     *
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/23-10:01
     **/
    public void shutDown() {
        if (null == CLIENT) {
            CLIENT.shutdown();
        }
    }
}
