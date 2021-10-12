package xyz.wongs.drunkard.base.message.response;

/**
 * 所有响应的超类，定义返回编码和消息描述
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/9/26 - 16:48
 * @since 1.0.0
 */
public abstract class Result {

    /**
     * 结果编码
     **/
    Integer code;
    /**
     * 消息描述
     **/
    String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
