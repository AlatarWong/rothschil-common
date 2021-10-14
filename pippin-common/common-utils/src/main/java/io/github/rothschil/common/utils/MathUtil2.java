package io.github.rothschil.common.utils;

import cn.hutool.core.math.MathUtil;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 21/4/13 15:30
 * @since 1.0.0
 */
public class MathUtil2 extends MathUtil {

    /**
     * @param input 输入
     * @param vector 向量，用以取整，入 10000/vector
     * @return int
     * @date 21/4/13 15:34
     */
    public static int getInteger(double input,int vector){
        double temp = input / vector;
        return (int)Math.ceil(temp);
    }
}
