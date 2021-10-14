package io.github.rothschil.design.adapter;

import lombok.extern.slf4j.Slf4j;

/** 当前现有资源，这里对一个的资源是国标电压220V
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 17:16
 * @since 1.0.0
 */
@Slf4j
public class Current {

    /**
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-19:28
     * @param
     * @return int
     **/
    public int nationalStandard(){
        log.debug("标准电压输出");
        return 220;
    }
}
