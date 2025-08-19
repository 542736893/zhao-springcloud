package com.zhao.inventory.domain.model.valueobject;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 库存ID值对象
 */
@Getter
public class InventoryId {
    
    private Long value;
    
    private static final AtomicLong SEQUENCE = new AtomicLong(1);
    
    private InventoryId(Long value) {
        this.value = value;
    }
    
    /**
     * 生成新的库存ID
     */
    public static InventoryId generate() {
        return new InventoryId(SEQUENCE.getAndIncrement());
    }
    
    /**
     * 从Long值创建库存ID
     */
    public static InventoryId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("库存ID必须大于0");
        }
        return new InventoryId(value);
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
