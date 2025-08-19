package com.zhao.order.domain.model.valueobject;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 订单ID值对象
 */
@Getter
public class OrderId {
    
    private Long value;
    
    private static final AtomicLong SEQUENCE = new AtomicLong(1);
    
    private OrderId(Long value) {
        this.value = value;
    }
    
    /**
     * 生成新的订单ID
     */
    public static OrderId generate() {
        return new OrderId(SEQUENCE.getAndIncrement());
    }
    
    /**
     * 从Long值创建订单ID
     */
    public static OrderId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("订单ID必须大于0");
        }
        return new OrderId(value);
    }
    
    /**
     * 设置值（用于持久化）
     */
    public void setValue(Long value) {
        this.value = value;
    }
    
    /**
     * 获取Long值
     */
    public Long getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
