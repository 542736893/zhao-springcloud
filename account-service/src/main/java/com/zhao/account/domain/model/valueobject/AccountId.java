package com.zhao.account.domain.model.valueobject;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 账户ID值对象
 */
@Getter
public class AccountId {
    
    private Long value;
    
    private static final AtomicLong SEQUENCE = new AtomicLong(1);
    
    private AccountId(Long value) {
        this.value = value;
    }
    
    /**
     * 生成新的账户ID
     */
    public static AccountId generate() {
        return new AccountId(SEQUENCE.getAndIncrement());
    }
    
    /**
     * 从Long值创建账户ID
     */
    public static AccountId of(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("账户ID必须大于0");
        }
        return new AccountId(value);
    }
    
    /**
     * 设置值（用于持久化）
     */
    public void setValue(Long value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountId accountId = (AccountId) obj;
        return value != null ? value.equals(accountId.value) : accountId.value == null;
    }
    
    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
