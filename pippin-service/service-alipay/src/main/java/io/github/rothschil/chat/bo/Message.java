package io.github.rothschil.chat.bo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 9:32
 * @since 1.0.0
 */
@Data
public class Message {

    public static final String ENTER = "ENTER";
    public static final String SPEAK = "SPEAK";
    public static final String QUIT = "QUIT";

    /**消息类型
     *
     */
    private String type;
    /**
     *  发送人
     */

    private String sender;

    /**
     * 发送消息
     */

    private String msg;

    /**
     * 在线用户数
     */
    private int online;

    public static String jsonStr(String type, String sender, String msg, int onlineTotal) {
        return JSON.toJSONString(new Message(type, sender, msg, onlineTotal));
    }

    public Message(String type, String sender, String msg, int online) {
        this.type = type;
        this.sender = sender;
        this.msg = msg;
        this.online = online;
    }
}
