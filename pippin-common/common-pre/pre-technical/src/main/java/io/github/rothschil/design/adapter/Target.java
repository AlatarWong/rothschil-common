package io.github.rothschil.design.adapter;

/**
 *  最终对外输出的标准
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 17:17
 * @since 1.0.0
 */
public interface Target {

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-19:28
     * @return int
     **/
    int nationalStandard();

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-19:28
     * @return int
     **/
    int euStandards();

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-19:28
     * @return int
     **/
    int japaneseStandard();
}
