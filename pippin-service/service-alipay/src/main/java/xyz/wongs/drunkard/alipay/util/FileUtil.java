package xyz.wongs.drunkard.alipay.util;

import xyz.wongs.drunkard.alipay.config.Constants;

import java.io.File;
import java.io.IOException;

/** 文件工具类
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 15:18
 * @version 1.0.0
 */
public class FileUtil {

    public static void existsFolder(String input) {
        String directory = input.substring(0,input.lastIndexOf(Constants.SLASH));
        File fileDirectory = new File(directory);
        try {
            // 文件夹不存在 创建
            if(!fileDirectory.exists()){
                fileDirectory.setWritable(true);
                fileDirectory.mkdirs();
            }
            File file = new File(input);
            if(!file.exists()){
                new File(input).createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String suffix(String input) {
        return input.substring(input.lastIndexOf(Constants.POINT));
    }
}
