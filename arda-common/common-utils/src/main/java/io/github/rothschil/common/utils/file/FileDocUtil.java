package io.github.rothschil.common.utils.file;

import io.github.rothschil.common.utils.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: <a href="https://github.com/rothschil">Sam</a>
 * @create: 2017/12/29 13:12
 * @Modified By :
 **/
public class FileDocUtil {

    private static Logger logger = LoggerFactory.getLogger(FileDocUtil.class);

    /**
     * 换行字符
     */
    private static final String ENTER_KEY_1= "\r\n";
    private static final String ENTER_KEY_2= "\n";

    /**
     *
     */
    public static final int SCAN_NUMBER = 5 * 60;

    public static final int ONE_SECOND = 1000;


    /**
     * 每次休眠时间
     */
    public static final int SCAN_SECOND = 10;



    /**
     * 方法实现说明
     * @method      compBean
     * @author      <a href="https://github.com/rothschil">Sam</a>
     * @since
     * @see
     * @param bean   组装的Bean
     * @param str   字符
     * @param isCheck
     * @param regex   分割字符
     * @return      int
     * @exception
     * @date        2018/4/9 10:22
     */
    public static int compBean(Object bean,String str,Boolean isCheck,String regex){
        String[] s = str.split(regex);
        return Reflections.getFieldValueMap(s,bean,isCheck);
    }

    /**
     * 方法实现说明：替换字符串中的换行：\r\n、\n返回一个新的字符串
     * @method      repalceEnterKey
     * @author      <a href="https://github.com/rothschil">Sam</a>
     * @since
     * @see
     * @param str
     * @return      java.lang.String
     * @exception
     * @date        2018/4/9 11:01
     */
    public static String repalceEnterKey(String str){
        if(str.contains(ENTER_KEY_1)){
            str = str.replace("\r\n","");
        }
        if(str.contains(ENTER_KEY_2)){
            str = str.replace("\n","");
        }
        return str;
    }

    /**
     * 方法实现说明
     * @method      compBean
     * @author      <a href="https://github.com/rothschil">Sam</a>
     * @since
     * @see
     * @param bean   组装的Bean
     * @param str   字符
     * @param regex   分割字符
     * @return      int
     * @exception
     * @date        2018/4/9 10:22
     */
    public static int compBean(Object bean,String str,String regex){
        return compBean(bean,str,false,regex);
    }


}
