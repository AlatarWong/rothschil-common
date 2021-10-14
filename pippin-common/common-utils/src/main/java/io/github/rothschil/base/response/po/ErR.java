package io.github.rothschil.base.response.po;

import io.github.rothschil.base.response.enums.Status;

import java.io.Serializable;

/**
 * 定义的异常错误实体类
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/18 10:42
 * @since 1.0.0
 */
public class ErR extends Result implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 错误
     **/
    private String exception;

    public static ErR fail(Status status, Throwable e, String message) {
        ErR erR = ErR.fail(status, e);
        erR.setMessage(message);
        return erR;
    }

    public static ErR fail(Status status, Throwable e) {
        ErR erR = new ErR();
        erR.setCode(status.getStatus());
        erR.setMessage(status.getMsg());
        erR.setException(e.getClass().getName());
        return erR;
    }

    public static ErR fail(Integer code, String message) {
        ErR erR = new ErR();
        erR.setCode(code);
        erR.setMessage(message);
        return erR;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
