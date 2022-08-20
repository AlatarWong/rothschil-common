package io.github.rothschil.base.persistence.redis.enums;

import org.redisson.client.protocol.RedisCommands;
import org.redisson.client.protocol.RedisStrictCommand;

import java.util.Arrays;
import java.util.Map;


/**
 * 延迟队列枚举
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2020/11/1 - 21:04
 * @since 1.0.0
 */
public enum CommandEunm {

    /**
     * 内存、键值对、cpu、服务
     */
    MEM(1, RedisCommands.INFO_MEMORY),
    KEY_SPACE(2, RedisCommands.INFO_KEYSPACE),
    CPU(3, RedisCommands.INFO_CPU),
    SERVER(4, RedisCommands.INFO_SERVER),
    UN_KNONW(5, RedisCommands.INFO_MEMORY);

    CommandEunm() {
    }

    CommandEunm(Integer code, RedisStrictCommand<Map<String, String>> command) {
        this.code = code;
        this.command = command;
    }

    /**
     * 根据输入类型选择相应的 RedisCommands，当不存在定义的 RedisCommands ，则默认为 查询内存
     *
     * @param key 输入类型
     * @return CommandEunm
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/11/11-17:22
     **/
    public static CommandEunm commandEunmType(Integer key) {
        CommandEunm[] alarmGrades = CommandEunm.values();
        CommandEunm result = Arrays.asList(alarmGrades).stream()
                .filter(alarmGrade -> alarmGrade.getCode().equals(key)).findFirst().orElse(CommandEunm.UN_KNONW);
        return result;
    }


    /**
     * 延迟队列 Redis Key
     */
    private Integer code;

    private RedisStrictCommand<Map<String, String>> command;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public RedisStrictCommand<Map<String, String>> getCommand() {
        return command;
    }

    public void setCommand(RedisStrictCommand<Map<String, String>> command) {
        this.command = command;
    }
}
