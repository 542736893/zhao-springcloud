package com.zhao.inventory.domain.model.valueobject;

import lombok.Value;

/**
 * 商品ID值对象
 */
@Value
public class ProductId {
    
    private final Long value;
    
    private ProductId(Long value) {
        this.value = value;
    }
    
    /**
     * 从Long值创建商品ID
     */
    public static ProductId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("商品ID必须大于0");
        }
        return new ProductId(value);
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
