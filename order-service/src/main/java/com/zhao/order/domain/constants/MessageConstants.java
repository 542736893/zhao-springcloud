package com.zhao.order.domain.constants;

/**
 * 消息常量类
 */
public class MessageConstants {
    
    /**
     * 订单相关Topic
     */
    public static final String ORDER_TOPIC = "order-topic";
    
    /**
     * 订单消息Tag
     */
    public static final String ORDER_CREATED_TAG = "order-created";
    public static final String ORDER_UPDATED_TAG = "order-updated";
    public static final String ORDER_PAID_TAG = "order-paid";
    public static final String ORDER_CANCELLED_TAG = "order-cancelled";
    
    /**
     * 消息类型
     */
    public static final String MESSAGE_TYPE_ORDER_CREATED = "ORDER_CREATED";
    public static final String MESSAGE_TYPE_ORDER_UPDATED = "ORDER_UPDATED";
    public static final String MESSAGE_TYPE_ORDER_PAID = "ORDER_PAID";
    public static final String MESSAGE_TYPE_ORDER_CANCELLED = "ORDER_CANCELLED";
    public static final String MESSAGE_TYPE_CUSTOM = "CUSTOM";
    
    /**
     * 消费者组
     */
    public static final String ORDER_CONSUMER_GROUP = "order-consumer-group";
}
