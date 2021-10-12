package xyz.wongs.drunkard.base.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 其他工具类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @since 1.0.0
 * @date 2018/4/23 - 10:16
 */
public class Utils {

    private Utils() {
        // No instances.
    }

    public static String toAmount(long amount) {
        return new BigDecimal(amount).divide(new BigDecimal(100)).toString();
    }

    public static boolean isEmpty(Object object) {
        if (object instanceof String) {
            return StringUtils.isEmpty((String) object);
        }
        return object == null;
    }

    public static <T> boolean isListNotEmpty(List<T> list) {
        return list != null && list.size() > 0;
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return !isListNotEmpty(list);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
