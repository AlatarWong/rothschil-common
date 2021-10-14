package io.github.rothschil.war.util;

import java.util.Random;

/**
 * @author WCNGS@QQ.CO
 * @date 2018/7/1 1:03
 * @since V1.0
 **/
public class AreaCodeStringUtils {

    /**
     * 根据层级，对 110102003025 这样的编码进行拆解，自左至右，按照2位 进行分割
     *
     * @param locationCode 编码
     * @param level        层级
     * @return String
     * @author WCNGS@QQ.COM
     * @date 2018/7/1 9:33
     */
    public static String getUrlStrByLocationCode(String locationCode, int level) {
        int bit = (level - 1) * 2;
        String subStr = locationCode.substring(0, bit);
        char[] ch = subStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bit; i++) {
            sb.append(ch[i]);
            if (i != 0 && (i + 1) % 2 == 0) {
                sb.append("/");
            }
        }
        return sb.toString();
    }

    public static int getSecond(int bound) {
        return new Random().nextInt(bound);
    }

    public static String bytes2HexString(byte[] b) {
        StringBuilder ret = new StringBuilder();
        for (byte value : b) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * int 转化为字节数组
     *
     * @param num int
     * @return byte
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/10/13-16:14
     **/
    public static byte[] intTobyte(int num) {
        return new byte[]{(byte) ((num >> 24) & 0xff), (byte) ((num >> 16) & 0xff), (byte) ((num >> 8) & 0xff), (byte) (num & 0xff)};
    }

    public static void main(String[] args) {
//        System.out.println(bytes2HexString(intTobyte(123)));
        System.out.println(Integer.toHexString(20));
        System.out.println( 1 + 0x14);
    }
}
