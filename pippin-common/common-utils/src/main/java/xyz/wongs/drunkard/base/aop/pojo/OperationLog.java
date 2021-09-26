package xyz.wongs.drunkard.base.aop.pojo;

import lombok.*;
import xyz.wongs.drunkard.base.po.BasePo;

import java.util.Date;

/**
 * 操作日志实体，可用于持久化
 * @author WCNGS@QQ.COM
 * @date 2019/12/3 15:06
 * @Version 1.0.0
*/
@EqualsAndHashCode(callSuper=false)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog extends BasePo<Long> {
    /**
     * 实例化ID
     */
    private Long id;

    /**
     * IP 信息
     */
    private String ipAddress;

    /**
     * 日志类型
     */
    private String type;

    /**
     * 名称
     */
    private String logName;

    /**
     * 动作URL
     */
    private String actionUrl;

    /**
     * 账号信息
     */
    private String operator;

    /**
     * 请求方法类型
     */
    private String httpMethod;

    /**
     * 请求头信息 user-Agent
     */
    private String userAgent;

    /**
     * 类信息
     */
    private String className;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 入参
     */
    private String parameters;

    /**
     * 方法描述
     */
    private String desc;


    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 处理结束时间
     */
    private Date endTime;

    /**
     * 耗时，单位 毫秒
     */
    private Long cost;

    /**
     * 是否成功
     */
    private int succeed;

    /**
     * 错误信息
     */
    private String err;

    /**
     * 请求报文
     */
    private String reqContent;

    /**
     * 响应内容
     */
    private String respContent;

}