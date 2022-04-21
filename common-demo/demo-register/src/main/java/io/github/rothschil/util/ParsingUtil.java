package io.github.rothschil.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 综合工具类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/24 - 11:56
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Slf4j
public class ParsingUtil {

    private ParsingUtil() {
    }

    public static Class<?> getClazz(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }



    public static <T> Iterable<T> listConversIterable(List<T> list) {
        return () -> new Iterator<T>() {
            ListIterator<T> listIterator = list.listIterator(list.size());

            @Override
            public boolean hasNext() {
                return listIterator.hasPrevious();
            }

            @Override
            public T next() {
                return listIterator.previous();
            }

            @Override
            public void remove() {
                listIterator.remove();
            }
        };

    }
    public static boolean exists(File tmpFile) {
        return tmpFile.exists();
    }
}
