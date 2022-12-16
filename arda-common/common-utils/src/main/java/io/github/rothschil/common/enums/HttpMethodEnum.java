package io.github.rothschil.common.enums;

/** Http类型枚举
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 14:37
 * @since 1.0.0
 */
public enum HttpMethodEnum {

    /**
     * GET
     */
    GET("GET"),


    /**
     * POST
     */
    POST("POST"),

    /**
     * , POST, PUT, PATCH, DELETE, OPTIONS, TRACE
     */
    HEAD("HEAD"),
    /**
     * PUT
     */
    PUT("PUT"),

    /**
     * PATCH
     */
    PATCH("PATCH"),

    /**
     * OPTIONS
     */
    OPTIONS("OPTIONS"),

    /**
     * TRACE
     */
    TRACE("TRACE"),

    /**
     * DELETE
     */
    DELETE("DELETE");

    /**
     *
     */
    private String code;


    HttpMethodEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
