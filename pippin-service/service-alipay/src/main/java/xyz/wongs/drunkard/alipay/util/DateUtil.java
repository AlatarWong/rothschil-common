package xyz.wongs.drunkard.alipay.util;

import org.apache.commons.lang.time.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil extends DateUtils {

    static String PARTEN = "yyyyMMddHHmmss";

    public static String getTransId(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(PARTEN);
        return dtf2.format(dateTime);
    }
}
