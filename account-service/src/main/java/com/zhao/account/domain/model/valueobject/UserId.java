package com.zhao.account.domain.model.valueobject;

import lombok.Value;

/**
 * 用户ID值对象
 */
@Value
public class UserId {
    
    private final Long value;
    
    private UserId(Long value) {
        this.value = value;
    }
    
    /**
     * 从Long值创建用户ID
     */
    public static UserId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("用户ID必须大于0");
        }
        return new UserId(value);
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
