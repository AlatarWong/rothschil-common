package io.github.rothschil.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfiguration {


    // ${cache} 获取配置文件的配置信息   #{}是spring表达式，获取Bean对象的属性
    @Value("#{${cache}}")
    private Map<String, Long> ttlParams;

    @Bean("synergyCacheManager")
    public CacheManager synergyCacheManager() {
        return initCacheManager();
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        return Redisson.create(Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
    }


    private CacheManager initCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        // 把各个cache注册到cacheManager中，Caffeine实现了org.springframework.cache.Cache接口
        simpleCacheManager.setCaches(Arrays.asList(
                buildCaffeineCache("TIMEOUT_10S",2000),
                buildCaffeineCache("TIMEOUT_60S",2000),
                buildCaffeineCache("TIMEOUT_600S",1000)
        ));
        return simpleCacheManager;
    }

    protected CaffeineCache buildCaffeineCache(String name,int maximumSize){
        return new CaffeineCache(
                name,Caffeine.newBuilder()
                .initialCapacity(50)
                        .maximumSize(maximumSize)
                        .expireAfterWrite(Duration.ofMinutes(1))
                .recordStats()
                        .build()
        );
    }

}
