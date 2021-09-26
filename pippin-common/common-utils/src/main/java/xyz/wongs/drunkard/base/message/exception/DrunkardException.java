package xyz.wongs.drunkard.base.message.exception;

import xyz.wongs.drunkard.base.message.enums.Status;


/**
 * @ClassName DrunkardException
 * @Description 自定义异常
 * @author WCNGS@QQ.COM
 * 
 * @date 2019/9/21 10:07
 * @Version 1.0.0
*/
public class DrunkardException extends RuntimeException{

    private static final long serialVersionUID = -6370612186038915645L;

    /**
     * 错误码
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String message;

    public DrunkardException() {
        super();
    }

    public DrunkardException(Status status) {
        super(status.getMsg());
        this.status = status.getStatus();
        this.message = status.getMsg();
    }

    public DrunkardException(Status status, Throwable cause) {
        super(status.getMsg(), cause);
        this.status = status.getStatus();
        this.message = status.getMsg();
    }

    public DrunkardException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }


    public DrunkardException(String message) {
        super(message);
        this.setStatus(-1);
        this.message = message;
    }

    public DrunkardException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public DrunkardException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
