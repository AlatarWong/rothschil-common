package io.github.rothschil.base.persistence.redis.util;

import io.github.rothschil.base.persistence.redis.bo.RedisInfo;
import io.github.rothschil.base.persistence.redis.enums.CommandEunm;
import org.redisson.client.RedisConnection;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.RedisCommands;
import org.redisson.client.protocol.RedisStrictCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Redis可视化监控
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/1 - 11:02
 * @since 1.0.0
 */
public class RedisUtil {

    private RedisConnection redisConnection;

    @Autowired
    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public List<RedisInfo> redisInfo(RedisStrictCommand<Map<String, String>> command) {
        List<RedisInfo> redisInfoList = new ArrayList<>();
        Map<String,String> mapInfo = redisConnection.sync(StringCodec.INSTANCE, command);
        mapInfo.forEach((k,v)->{
            RedisInfo info = new RedisInfo(k,v,RedisInfo.map.get(k));
            redisInfoList.add(info);
        });
        return redisInfoList;
    }

    public List<RedisInfo> redisInfo(Integer type) {
        return redisInfo(CommandEunm.commandEunmType(type).getCommand());
    }
}
