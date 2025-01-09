package io.github.rothschil.common.cache;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    @Autowired
    @Qualifier("redisCacheManager")
    private CacheManager redisCacheManager;

    @Around("@annotation(org.springframework.cache.annotation.Cacheable)")
    public Object aroundCacheable(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // 从Caffeine缓存中获取数据
        Cache caffeineCache = caffeineCacheManager.getCache(methodName);
        if (caffeineCache != null) {
            Object cachedResult = caffeineCache.get(args[0], Object.class);
            if (cachedResult != null) {
                return cachedResult;
            }
        }

        // 从Redis缓存中获取数据
        Cache redisCache = redisCacheManager.getCache(methodName);
        if (redisCache != null) {
            Object cachedResult = redisCache.get(args[0], Object.class);
            if (cachedResult != null) {
                // 将数据放入Caffeine缓存
                assert caffeineCache != null;
                caffeineCache.put(args[0], cachedResult);
                return cachedResult;
            }
        }

        // 调用实际方法
        Object result = joinPoint.proceed();

        // 将结果放入Caffeine和Redis缓存
        if (caffeineCache != null) {
            caffeineCache.put(args[0], result);
        }
        if (redisCache != null) {
            redisCache.put(args[0], result);
        }

        return result;
    }
}
