package io.github.rothschil.common.utils.security;

import io.github.rothschil.common.utils.StringUtils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Md5加密方法
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @ClassName Md5Utils
 * @Description
 * 
 * @date 2017/12/15 22:50
 * @since 1.0.0
 */
public class SignatureUtils {

    private static final Logger log = LoggerFactory.getLogger(SignatureUtils.class);

    /**
     * 生成含有随机盐的密码
     *
     * @param password 要加密的密码
     * @return String 含有随机盐的密码
     */
    public static String getSalt4Md5(String password, String salts) {
        password = md5Hex(password + salts);
        int size = 48;
        int stride = 3;
        int bic = 2;
        char[] cs = new char[size];
        for (int i = 0; i < size; i += stride) {
            cs[i] = password.charAt(i / stride * bic);
            char c = salts.charAt(i / stride);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / stride * bic + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * MD5加密,并把结果由字节数组转换成十六进制字符串
     *
     * @param str 要加密的内容
     * @return String 返回加密后的十六进制字符串
     */
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return hex(digest);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }

    /**
     * byte[]字节数组 转换成 十六进制字符串
     *
     * @param arry 要转换的byte[]字节数组
     * @return java.lang.String 返回十六进制字符串
     * @throws
     * @Description
     * @date 2017/12/15 23:23
     */
    private static String hex(byte[] arry) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arry.length; ++i) {
            sb.append(Integer.toHexString((arry[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


    /**
     * @param password 原密码
     * @param md5Str   加密之后的密码
     * @param salt     salt
     * @return boolean  true:密码一致；false：不一致
     * @throws
     * @Description
     * @date 2017/12/15 23:21
     */
    public static boolean getSaltverify4Md5(String password, String md5Str, String salt) {
        int size = 48;
        int stride = 3;
        int bic = 2;
        char[] cars = new char[bic * (bic << stride)];
        for (int i = 0; i < size; i += stride) {
            cars[i / stride * bic] = md5Str.charAt(i);
            cars[i / stride * bic + 1] = md5Str.charAt(i + bic);
        }
        return md5Hex(password + salt).equals(String.valueOf(cars));
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            log.error("not supported charset...{}", e);
            return s;
        }
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    public static String getMd5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, String> appSecretkey = new HashMap<>();

    /**
     * 用于生成数字签名
     */
    public static final String MAC_ALGORITHM_DEFAULT = "HmacSHA1";

    /**
     * 为每个appid生成唯一的secret_key（签名密钥）
     * @param appId 应用唯一标识
     * @return
     */
    public static String getSecretKey(String appId) {
        String secretKey = UUID.randomUUID().toString().replaceAll("-", "");
        appSecretkey.put(appId, secretKey);
        return secretKey;
    }

    /**
     * 对消息进行签名
     * @param msg 消息内容
     * @param secretKey 签名密钥
     * @return
     */
    public static String getSignature(String msg, String secretKey) {
        return hamcsha1(msg.getBytes(), secretKey.getBytes());
    }

    /**
     * 获取基于哈希的消息验证代码
     * @param data 消息内容字节数组
     * @param key 签名密钥字节数组
     * @return
     */
    private static String hamcsha1(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, MAC_ALGORITHM_DEFAULT);
            Mac mac = Mac.getInstance(MAC_ALGORITHM_DEFAULT);
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转16进制
     * @param b 字节数组
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 接受方校验签名
     * @param appId 应用唯一标识
     * @param signatrue 签名结果
     * @param msg 消息内容
     * @return
     */
    public static boolean verifySignature(String appId, String signatrue, String msg) {
        String secretKey = appSecretkey.get(appId);
        if (StringUtils.isBlank(secretKey)) {
            return false;
        }
        String checkSignatrue = hamcsha1(msg.getBytes(), secretKey.getBytes());
        return checkSignatrue.equals(checkSignatrue);
    }

}

