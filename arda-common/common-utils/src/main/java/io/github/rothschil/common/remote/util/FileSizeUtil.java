package io.github.rothschil.common.remote.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;


/**
 * 获取文件夹或文件的大小 以B、KB、MB、GB 为单位
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/11/1 - 15:31
 * @since 1.0.0
 */
@Slf4j
public class FileSizeUtil {

    /**
     * 获取文件大小单位为B的double值
     */
    public static final int UNIT_B = 1;

    /**
     * 获取文件大小单位为KB的double值
     */
    public static final int UNIT_KB = 2;
    /**
     * 获取文件大小单位为MB的double值
     */
    public static final int UNIT_MB = 3;
    /**
     * 获取文件大小单位为GB的double值
     */
    public static final int UNIT_GB = 4;

    /**
     * 获取指定文件或指定文件夹的的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFolderOrFileSize(String filePath, int sizeType) {
        return getFolderOrFileSize(new File(filePath), sizeType);
    }

    /**
     * 获取指定文件或指定文件夹的的指定单位的大小
     *
     * @param file     文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFolderOrFileSize(File file, int sizeType) {
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFolderSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取文件大小失败!");
        }
        return formatSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFolderOrFileSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFolderSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取文件大小失败!");
        }
        return formatFileSize(blockSize);
    }

    /**
     * 获取指定文件的大小
     *
     * @param file 文件File
     * @return long 文件大小
     */
    private static long getFileSize(File file) {
        long size = 0;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                if(file.createNewFile()){
                    log.error("文件不存在!，重新创建成功");
                } else {
                    log.error("文件不存在!，重新创建失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /**
     * 获取指定文件夹的大小
     *
     * @param file 文件File
     * @return long 文件大小
     */
    private static long getFolderSize(File file) {
        long size = 0;
        File[] files = file.listFiles();
        assert files != null;
        for (File temp : files) {
            if (temp.isDirectory()) {
                size = size + getFolderSize(temp);
            } else {
                size = size + getFileSize(temp);
            }
        }
        return size;
    }


    private final static String DECIMAL_FORMAT = "#.00";

    private final static int CONVERT = 0X400;
    private final static int CONVERT_KB = 0X100000;
    private final static int CONVERT_MB = 0X40000000;

    /**
     * 转换文件大小
     *
     * @param fileSize 文件大小
     * @return 字符串格式的
     */
    public static String formatFileSize(long fileSize) {
        String defaultSize = "0B";
        if (fileSize == 0) {
            return defaultSize;
        }
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        String fileSizeString;
        if (fileSize < CONVERT) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < CONVERT_KB) {
            fileSizeString = df.format((double) fileSize / CONVERT) + "KB";
        } else if (fileSize < CONVERT_MB) {
            fileSizeString = df.format((double) fileSize / CONVERT_KB) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / CONVERT_MB) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileSize 文件大小
     * @param unit     文件大小单位
     * @return double 文件大小
     */
    private static double formatSize(long fileSize, int unit) {
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        double size;
        switch (unit) {
            case UNIT_B:
                size = Double.parseDouble(df.format((double) fileSize));
                break;
            case UNIT_KB:
                size = Double.parseDouble(df.format((double) fileSize / CONVERT));
                break;
            case UNIT_MB:
                size = Double.parseDouble(df.format((double) fileSize / CONVERT_KB));
                break;
            case UNIT_GB:
                size = Double.parseDouble(df.format((double) fileSize / CONVERT_MB));
                break;
            default:
                size = 0;
                break;
        }
        return size;
    }
}