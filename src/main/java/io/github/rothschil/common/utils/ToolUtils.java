package io.github.rothschil.common.utils;

import org.springframework.cache.annotation.Cacheable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolUtils {


    /**
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param cacheable
     * @return String
     **/
    public static String buildContent(Cacheable cacheable) {
        String content = cacheable.key();
        String pattern = "\\w+\\.\\w+";
        if(ToolUtils.operation(content, pattern)){
            return "{#"+content+"}";
        }
        return content;
    }

    /**
     * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
     * @param content
     * @param pattern
     * @return boolean
     **/
    public static boolean operation(String content,String pattern){
        //String pattern = "[^{\\w+\\S}$]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        return m.matches();
    }
}
