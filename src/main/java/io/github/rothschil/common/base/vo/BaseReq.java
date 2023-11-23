package io.github.rothschil.common.base.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** 请求
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @since 1.0.0
 */
@SuperBuilder
@Data
@NoArgsConstructor
public class BaseReq {

    /**
     * 服务名
     */
    String serviceName;

    /**
     * 预处理
     *
     * @return
     */
    public boolean before() {
        return true;
    }
}
