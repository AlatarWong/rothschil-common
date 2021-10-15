package io.github.rothschil.base.aop.pojo;

import io.github.rothschil.common.po.BasePo;
import lombok.*;

import java.util.Date;

/**
 * 操作日志实体，可用于持久化
 *
 * @author WCNGS@QQ.COM
 * @date 2019/12/3 15:06
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppLog extends BasePo<Long> {
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
    private String url;

    /**
     * 操作人信息
     */
    private String operator;

    /**
     * 请求方法类型
     */
    private String httpType;

    /**
     * 请求头信息 user-Agent
     */
    private String userAgent;

    /**
     * 类信息
     */
    private String clazz;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 入参，持久化过程中，需要 设置存储类型为 CLOB 或者
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
     * 头信息，需要 设置存储类型为 CLOB 或者
     */
    private String headers;

    /**
     * 响应内容，需要 设置存储类型为 CLOB 或者
     */
    private String respContent;

}