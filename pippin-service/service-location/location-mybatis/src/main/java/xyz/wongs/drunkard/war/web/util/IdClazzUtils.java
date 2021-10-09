//package xyz.wongs.drunkard.war.web.util;
//
//
///**
// * @author WCNGS@QQ.COM
// * @date 2020/9/9 15:28
// * @since 1.0.0
//*/
//public class IdClazzUtils {
//
//    private final static RedisUidService redisUidService;
//
//    static {
//        redisUidService = SpringContextHolder.getBean(RedisUidService.class);
//    }
//
//    /**
//     * @Description
//     * @param clazz
//     * @return java.lang.Long
//     * @throws
//     * @date 2020/9/9 15:28
//     */
//    public static long getId(Class<?> clazz){
//        return redisUidService.generate(clazz.getSimpleName().toUpperCase());
//    }
//
//}
