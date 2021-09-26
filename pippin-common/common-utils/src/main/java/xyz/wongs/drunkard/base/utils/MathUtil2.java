package xyz.wongs.drunkard.base.utils;

import cn.hutool.core.math.MathUtil;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @ClassName MathUtil2
 * @Description
 * 
 * @date 21/4/13 15:30
 * @Version 1.0.0
 */
public class MathUtil2 extends MathUtil {

    /**
     * @Description
     * @param input 输入
     * @param vector 向量，用以取整，入 10000/vector
     * @return int
     * @throws
     * @date 21/4/13 15:34
     */
    public static int getInteger(double input,int vector){
        double temp = input / vector;
        return (int)Math.ceil(temp);
    }
}
