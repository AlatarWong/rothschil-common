package io.github.rothschil.base.persistence.redis.enums;

/**
 * 延迟队列枚举
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2020/11/1 - 21:04
 * @since 1.0.0
 */
public enum DelayQueueEnum {

    /**
     * 支付超时
     */
    ORDER_PAYMENT_TIMEOUT("ORDER_PAYMENT_TIMEOUT", "订单支付超时，自动取消订单", "orderPaymentTimeout"),

    /**
     * 超时未评价，默认
     */
    ORDER_TIMEOUT_NOT_EVALUATED("ORDER_TIMEOUT_NOT_EVALUATED", "订单超时未评价，系统默认好评", "orderTimeoutNotEvaluated");

    DelayQueueEnum() {
    }

    DelayQueueEnum(String code, String name, String beanId) {
        this.code = code;
        this.name = name;
        this.beanId = beanId;
    }

    /**
     * 延迟队列 Redis Key
     */
    private String code;

    /**
     * 中文描述
     */
    private String name;

    /**
     * 延迟队列具体业务实现的 Bean
     * 可通过 Spring 的上下文获取
     */
    private String beanId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }
}
