package com.zhao.account.domain.model.valueobject;

/**
 * 账户状态值对象
 */
public enum AccountStatus {
    
    /**
     * 正常
     */
    ACTIVE("ACTIVE", "正常"),
    
    /**
     * 冻结
     */
    FROZEN("FROZEN", "冻结"),
    
    /**
     * 关闭
     */
    CLOSED("CLOSED", "关闭"),
    
    /**
     * 待激活
     */
    PENDING("PENDING", "待激活"),
    
    /**
     * 已注销
     */
    CANCELLED("CANCELLED", "已注销");
    
    private final String code;
    private final String description;
    
    AccountStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据代码获取状态
     */
    public static AccountStatus fromCode(String code) {
        for (AccountStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的账户状态代码: " + code);
    }
    
    /**
     * 是否为活跃状态
     */
    public boolean isActive() {
        return this == ACTIVE;
    }
    
    /**
     * 是否为冻结状态
     */
    public boolean isFrozen() {
        return this == FROZEN;
    }
    
    /**
     * 是否为关闭状态
     */
    public boolean isClosed() {
        return this == CLOSED;
    }
    
    /**
     * 是否可以进行交易
     */
    public boolean canTransact() {
        return this == ACTIVE;
    }
}
