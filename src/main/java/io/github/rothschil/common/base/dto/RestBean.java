package io.github.rothschil.common.base.dto;

import lombok.Data;

/** HTTP 请求相应的内容封装
 *
 * @author HeD
 * @date 2022/9/7 20:16
 */
@Data
public class RestBean {
    /**
     * http状态码
     */
    Integer code;
    /**
     * 响应内容
     */
    String resp;

    public RestBean fail() {
        RestBean restBean = new RestBean();
        restBean.setCode(500);
        return restBean;
    }

}
