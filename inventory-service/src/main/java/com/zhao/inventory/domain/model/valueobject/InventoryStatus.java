package com.zhao.inventory.domain.model.valueobject;

/**
 * 库存状态值对象
 */
public enum InventoryStatus {
    
    /**
     * 有库存
     */
    IN_STOCK("IN_STOCK", "有库存"),
    
    /**
     * 低库存
     */
    LOW_STOCK("LOW_STOCK", "低库存"),
    
    /**
     * 已预占
     */
    RESERVED("RESERVED", "已预占"),
    
    /**
     * 缺货
     */
    OUT_OF_STOCK("OUT_OF_STOCK", "缺货"),
    
    /**
     * 已停用
     */
    DISABLED("DISABLED", "已停用");
    
    private final String code;
    private final String description;
    
    InventoryStatus(String code, String description) {
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
    public static InventoryStatus fromCode(String code) {
        for (InventoryStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的库存状态代码: " + code);
    }
    
    /**
     * 是否为可用状态
     */
    public boolean isAvailable() {
        return this == IN_STOCK || this == LOW_STOCK;
    }
    
    /**
     * 是否为缺货状态
     */
    public boolean isOutOfStock() {
        return this == OUT_OF_STOCK;
    }
    
    /**
     * 是否为低库存状态
     */
    public boolean isLowStock() {
        return this == LOW_STOCK;
    }
}
