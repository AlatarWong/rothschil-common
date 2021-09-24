package xyz.wongs.drunkard.alipay.util;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @version 1.0.0
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 11:12
 */
public class StringUtil {

    public static String convert(String input) {
        input = (input == null ? "" : input);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            sb.append("\\u");
            //取出高8位
            j = (c >>> 8);
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            //取出低8位
            j = (c & 0xFF);
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);

        }
        return (new String(sb));
    }

    public static String revert(String input) {
        input = (input == null ? "" : input);
        //如果不是unicode码则原样返回
        if (input.indexOf("\\u") == -1) {
            return input;

        }
        StringBuffer sb = new StringBuffer(1000);

        for (int i = 0; i < input.length() - 6; ) {
            String strTemp = input.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++) {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar) {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }
                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            sb.append((char) c);
            i = i + 6;
        }
        return sb.toString();
    }

    public static String toUnicode(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append("\\" + "u" + Integer.toHexString(input.charAt(i)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("\n"));
        // \u000d System.out.println("ppp");
        System.out.println(revert("\\u000d"));
    }
}
