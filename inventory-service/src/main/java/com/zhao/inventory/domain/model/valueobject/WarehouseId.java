package com.zhao.inventory.domain.model.valueobject;

import lombok.Value;

/**
 * 仓库ID值对象
 */
@Value
public class WarehouseId {
    
    private final Long value;
    
    private WarehouseId(Long value) {
        this.value = value;
    }
    
    /**
     * 从Long值创建仓库ID
     */
    public static WarehouseId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("仓库ID必须大于0");
        }
        return new WarehouseId(value);
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
