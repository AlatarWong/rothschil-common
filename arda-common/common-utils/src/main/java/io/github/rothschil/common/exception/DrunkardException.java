package io.github.rothschil.common.exception;

import io.github.rothschil.base.response.enums.Status;


/**
 * 属于自定义异常，使用过程中，需要注意构造函数 {@link Status} 中的定义，是否满足要求，不满足的话
 * 则扩展 {@link Status} 枚举值
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/9/21 10:07
 */
public class DrunkardException extends RuntimeException {

    private static final long serialVersionUID = -6370612186038915645L;

    /**
     * 错误码
     */
    private int status;

    /**
     * 错误信息
     */
    private String message;

    public DrunkardException() {
        super();
    }


    /**
     * 利用 {@link Status} 定义的枚举来创建异常信息
     *
     * @param status {@link Status} 类
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(Status status) {

        super(status.getMsg());
        this.status = status.getStatus();
        this.message = status.getMsg();
    }

    /**
     * 利用 {@link Status} 定义的枚举来创建异常信息
     *
     * @param status {@link Status} 类
     * @param append 补充内容
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(Status status, String append) {
        super(status.getMsg());
        this.status = status.getStatus();
        this.message = status.getMsg() + " Detailed information is " + append;
    }

    /**
     * 利用 {@link Status} 定义的枚举来创建异常信息
     *
     * @param status {@link Status} 类
     * @param cause  异常原因信息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(Status status, Throwable cause) {
        super(status.getMsg(), cause);
        this.status = status.getStatus();
        this.message = status.getMsg();
    }

    /**
     * 利用 {@link Status} 定义的枚举来创建异常信息
     *
     * @param message 定义的消息
     * @param cause   异常原因信息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    /**
     * 定义特殊的异常，状态码为 -1
     *
     * @param message 定义的消息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(String message) {
        super(message);
        this.setStatus(-1);
        this.message = message;
    }

    /**
     * 定义特殊的异常，可以自定义状态码和异常信息
     *
     * @param status  自定义状态码
     * @param message 定义的消息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    /**
     * 定义特殊的异常，可以自定义状态码和异常信息
     *
     * @param status  自定义状态码
     * @param message 定义的消息
     * @param cause   异常原因信息
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/8-12:00
     **/
    public DrunkardException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
