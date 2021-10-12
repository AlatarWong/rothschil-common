package xyz.wongs.drunkard.base.message.response;

import xyz.wongs.drunkard.base.message.enums.Status;

import java.io.Serializable;

/**
 * 定义正常响应实体类
 *
 * @author WCNGS@QQ.COM
 * @date 2020/8/2 13:48
 * @since 1.0.0
 */
public class R extends Result implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 对需要响应的数据进行封装
     */
    private Object data;

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
     * @date 2021/9/26-16:43
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
     * @date 2021/9/26-16:43
     **/
    public static R success(Object data) {
        R r = success();
        r.setData(data);
        return r;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
