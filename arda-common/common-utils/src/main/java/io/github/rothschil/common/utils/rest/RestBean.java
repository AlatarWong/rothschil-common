package io.github.rothschil.common.utils.rest;

import lombok.Data;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @since 1.0.0
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

    public static RestBean fail() {
        RestBean restBean = new RestBean();
        restBean.setCode(500);
        return restBean;
    }
}
