package io.github.rothschil.common.cache;


import com.alibaba.fastjson.JSON;
import io.github.rothschil.common.cache.enums.ChannelTopicEnum;
import io.github.rothschil.common.cache.listener.RedisPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;


public class SynergyCache extends AbstractValueAdaptingCache {
    Logger logger = LoggerFactory.getLogger(SynergyCache.class);

    /**
     * 缓存的名称
     */
    private final String name;

    /**
     * 是否使用一级缓存
     */
    private boolean usedFirstCache = true;

    private RedisCacheWriter cacheWriter;

    private RedisCacheConfiguration cacheConfig;

    private RedisCacheManager redisCacheManager;

    private Cache cache;

    /**
     * Caffeine缓存
     */
    private final CaffeineCache caffeineCache;

    RedisOperations<? extends Object, ? extends Object> redisOperations;

    /**
     *
     * String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig
     * @param name              缓存名称
     * @param prefix            缓存前缀
     * @param expiration        redis缓存过期时间
     * @param preloadSecondTime redis缓存自动刷新时间
     * @param allowNullValues   是否允许存NULL，默认是false
     * @param usedFirstCache    是否使用一级缓存，默认是true
     * @param forceRefresh      是否强制刷新（走数据库），默认是false
     * @param caffeineCache     Caffeine缓存
     */
    public SynergyCache(String name, byte[] prefix, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig,
                        long expiration, long preloadSecondTime, boolean allowNullValues, boolean usedFirstCache,
                        boolean forceRefresh, com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache) {

        super(allowNullValues);
        this.name = name;
        this.cacheWriter=cacheWriter;
        this.cacheConfig=cacheConfig;
        this.usedFirstCache = usedFirstCache;
        this.redisOperations = redisOperations;
        this.cache = RedisCacheManager.builder(cacheWriter).build().getCache(name);
        this.caffeineCache = new CaffeineCache(name, caffeineCache, allowNullValues);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }


    public CaffeineCache getFirstCache() {
        return this.caffeineCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        if (usedFirstCache) {
            // 查询一级缓存
            wrapper = caffeineCache.get(key);
            logger.debug("查询一级缓存 key:{},返回值是:{}", key, JSON.toJSONString(wrapper));
        }

        if (wrapper == null) {
            // 查询二级缓存
            wrapper = Objects.requireNonNull(cache).get(key);
            caffeineCache.put(key, wrapper == null ? null : wrapper.get());
            logger.debug("查询二级缓存,并将数据放到一级缓存。 key:{},返回值是:{}", key, JSON.toJSONString(wrapper));
        }
        return wrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        T value = null;
        if (usedFirstCache) {
            // 查询一级缓存
            value = caffeineCache.get(key, type);
            logger.debug("查询一级缓存 key:{},返回值是:{}", key, JSON.toJSONString(value));
        }

        if (value == null) {
            // 查询二级缓存
            value = Objects.requireNonNull(cache).get(key,type);
//            value = redisCache.get(key, type);
            caffeineCache.put(key, value);
            logger.debug("查询二级缓存,并将数据放到一级缓存。 key:{},返回值是:{}", key, JSON.toJSONString(value));
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T value = null;
        if (usedFirstCache) {
            // 查询一级缓存,如果一级缓存没有值则调用getForSecondaryCache(k, valueLoader)查询二级缓存
            value = (T) caffeineCache.getNativeCache().get(key, k -> getForSecondaryCache(k, valueLoader));
        } else {
            // 直接查询二级缓存
            value = (T) getForSecondaryCache(key, valueLoader);
        }

        if (value instanceof NullValue) {
            return null;
        }
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        if (usedFirstCache) {
            caffeineCache.put(key, value);
        }
        cache.put(key,value);
//        redisCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if (usedFirstCache) {
            caffeineCache.putIfAbsent(key, value);
        }
        return cache.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        // 删除的时候要先删除二级缓存再删除一级缓存，否则有并发问题
        cache.evict(key);
        if (usedFirstCache) {
            // 删除一级缓存需要用到redis的Pub/Sub（订阅/发布）模式，否则集群中其他服服务器节点的一级缓存数据无法删除
            Map<String, Object> message = new HashMap<>();
            message.put("cacheName", name);
            message.put("key", key);
            // 创建redis发布者
            RedisPublisher redisPublisher = new RedisPublisher(redisOperations, ChannelTopicEnum.REDIS_CACHE_DELETE_TOPIC.getChannelTopic());
            // 发布消息
            redisPublisher.publisher(message);
        }
    }

    @Override
    public void clear() {
        cache.clear();
        if (usedFirstCache) {
            // 清除一级缓存需要用到redis的订阅/发布模式，否则集群中其他服服务器节点的一级缓存数据无法删除
            Map<String, Object> message = new HashMap<>();
            message.put("cacheName", name);
            // 创建redis发布者
            RedisPublisher redisPublisher = new RedisPublisher(redisOperations, ChannelTopicEnum.REDIS_CACHE_CLEAR_TOPIC.getChannelTopic());
            // 发布消息
            redisPublisher.publisher(message);
        }
    }

    @Override
    protected Object lookup(Object key) {
        Object value = null;
        if (usedFirstCache) {
            value = caffeineCache.get(key);
            logger.debug("查询一级缓存 key:{},返回值是:{}", key, JSON.toJSONString(value));
        }
        if (value == null) {
            value = cache.get(key);
            logger.debug("查询二级缓存 key:{},返回值是:{}", key, JSON.toJSONString(value));
        }
        return value;
    }

    /**
     * 查询二级缓存
     *
     * @param key
     * @param valueLoader
     * @return
     */
    private <T> Object getForSecondaryCache(Object key, Callable<T> valueLoader) {
        T value = cache.get(key, valueLoader);
        logger.debug("查询二级缓存 key:{},返回值是:{}", key, JSON.toJSONString(value));
        return toStoreValue(value);
    }
}