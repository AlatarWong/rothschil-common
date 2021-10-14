package io.github.rothschil.common.utils.file;

import io.github.rothschil.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName FileUtil
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/8/31 16:37
 * @since 1.0.0
*/
public class FileUtil extends cn.hutool.core.io.FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new RuntimeException("文件未找到");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new RuntimeException("");
        } finally {
            try {
                if(null!=channel) {
                    channel.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("文件读取失败");
            }
            try {
                if(null!=channel) {
                    fs.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("文件读取失败");
            }
        }
    }

    /**
     * 删除目录
     *
     * @author fengshuonan
     * @date 2017/10/30 下午4:15
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

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