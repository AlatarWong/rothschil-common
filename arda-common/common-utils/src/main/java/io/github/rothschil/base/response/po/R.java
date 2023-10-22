package io.github.rothschil.base.response.po;

import io.github.rothschil.base.response.enums.Status;

import java.io.Serializable;

/**
 * 定义正常响应实体类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2017/12/2 13:48
 * @since 1.0.0
 */
public class R extends Result implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 对需要响应的数据进行封装
     */
    private Object data;

    /**
     * 错误
     **/
    private String exception;

    private R() {
    }

    public R(Status status, Object data) {
        this.code = status.getStatus();
        this.message = status.getMsg();
        this.data = data;
    }

    private void setStatus(Status status) {
        this.code = status.getStatus();
        this.message = status.getMsg();
    }


    /**
     * 成功
     *
     * @return Result
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-16:43
     **/
    public static R success() {
        R r = new R();
        r.setStatus(Status.SUCCESS);
        return r;
    }

    /**
     * 成功
     *
     * @param data 数据实体
     * @return Result
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2018/4/26-16:43
     **/
    public static R success(Object data) {
        R r = success();
        r.setData(data);
        return r;
    }

    public static R fail(Status status, Throwable e, String message) {
        R erR = R.fail(status, e);
        erR.setMessage(message);
        return erR;
    }

    public static R fail(Status status, Throwable e) {
        R erR = new R();
        erR.setCode(status.getStatus());
        erR.setMessage(status.getMsg());
        erR.setException(e.getClass().getName());
        return erR;
    }

    public static R fail(Integer code, String message) {
        R erR = new R();
        erR.setCode(code);
        erR.setMessage(message);
        return erR;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
