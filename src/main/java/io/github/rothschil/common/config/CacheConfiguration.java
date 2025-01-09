package io.github.rothschil.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.rothschil.common.cache.manager.PlusSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * //todo 添加类描述
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 */
@EnableCaching
@Configuration
public class CacheConfiguration {



//    @Bean
//    public RedissonClient redissonClient() throws IOException {
//        return Redisson.create(Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream()));
//    }


    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder().expireAfterWrite(600, TimeUnit.SECONDS).initialCapacity(100).maximumSize(1000).build();
    }

    /**
     * 自定义缓存管理器 整合spring-cache
     */
    @Bean
    public CacheManager cacheManager() {
        return new PlusSpringCacheManager();
    }

//    @Bean("caffeineCacheManager")
//    public CaffeineCacheManager caffeineCacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .maximumSize(500)
//                .expireAfterWrite(60, TimeUnit.SECONDS));
//        return cacheManager;
//    }
//
//
//    //设置缓存过期
//    @Bean("redisCacheManager")
//    public CacheManager redisCacheManager(RedissonClient redissonClient) {
//        MessageRedissonCacheManager cacheManager = new MessageRedissonCacheManager(redissonClient, getCacheManagerConfigMap(), new JsonJacksonCodec());
//        // 设置其他key的默认时间,已有默认24小时,空闲1小时;  设置时间为60秒,空闲30秒
//        cacheManager.setDefaultConfig(new CacheConfig(60 * 1000, 30 * 1000));
//        // 关闭空值的存储
//        cacheManager.setAllowNullValues(false);
//        return cacheManager;
//    }
//
//
//    //注解表示没有就创建bean
//    @Bean
//    @ConditionalOnMissingBean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        // key的序列化采用StringRedisSerializer
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        //使用fastjson序列化
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        // value值的序列化采用fastJsonRedisSerializer
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setHashValueSerializer(fastJsonRedisSerializer);
//        //redis提供的工厂
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//    //比较适用于单纯的key-value 比如redis分布式锁
//    @Bean
//    @ConditionalOnMissingBean(StringRedisTemplate.class)
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//
//
//
//    /**
//     * 定制缓存的时间 redisson的桶,cache的命名空间为维度  cacheName,value;
//     *
//     * @return Map
//     */
//    private Map<String, CacheConfig> getCacheManagerConfigMap() {
//        Map<String, CacheConfig> configMap = new HashMap<>(16);
//        //自定义cache的缓存时间  此处可以定义一些缓存,比如字典等等
//        //redisson的缓存以key以hash 桶的形式存在
//        // syhToken 过期时间是 12 小时,过期自动删除   空闲之间是1小时 1小时不被防范自动删除
//        configMap.put("syhToken", new CacheConfig(5 * 60 * 1000, 5 * 60 * 1000));
//        //token 过期时间1小时  最大空闲时间30分钟
//        configMap.put("token1", new CacheConfig(60 * 1000, 30 * 1000));
//        configMap.put("token666", new CacheConfig(24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000));
//        return configMap;
//    }


//    @Bean("redisCacheManager")
//    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(1))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }

}
