package com.zhao.order.domain.model.valueobject;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单号值对象
 */
@Value
public class OrderNumber {
    
    private final String value;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);
    
    private OrderNumber(String value) {
        this.value = value;
    }
    
    /**
     * 生成新的订单号
     */
    public static OrderNumber generate() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(FORMATTER);
        int sequence = SEQUENCE.getAndIncrement();
        if (sequence > 9999) {
            SEQUENCE.set(1);
            sequence = 1;
        }
        String orderNumber = String.format("%s%04d", timestamp, sequence);
        return new OrderNumber(orderNumber);
    }
    
    /**
     * 从字符串创建订单号
     */
    public static OrderNumber of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("订单号不能为空");
        }
        if (value.length() != 18) {
            throw new IllegalArgumentException("订单号长度必须为18位");
        }
        return new OrderNumber(value);
    }
    
    /**
     * 获取字符串值
     */
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
