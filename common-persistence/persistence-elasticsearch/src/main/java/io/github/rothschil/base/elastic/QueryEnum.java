package io.github.rothschil.base.elastic;

/**
 * 匹配类型定义
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/23 - 14:37
 * @since 1.0.0
 */
public enum QueryEnum {

    /**
     * 精确匹配
     */
    TERM_QUERY(11, "精确匹配"),


    /**
     * 前缀匹配
     */
    PREFIX_QUERY(12, "前缀匹配"),
    /**
     * 后缀匹配，多字段
     */
    SUFFIX_QUERY(13, "后缀多字段匹配"),
    /**
     * 后缀匹配，单字段
     */
    SUFFIX_SINGLE_QUERY(14, "后缀单字段匹配"),
    /**
     * 正则匹配
     */
    REG_QUERY(15, "前缀匹配"),
    /**
     * 前缀匹配
     */
    RANGE_QUERY(16, "区间匹配");

    /**
     *
     */
    private int code;
    private String coment;


    QueryEnum(int code, String coment) {
        this.code = code;
        this.coment = coment;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }
}
