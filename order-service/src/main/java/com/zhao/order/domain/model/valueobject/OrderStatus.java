package com.zhao.order.domain.model.valueobject;

import lombok.Getter;

/**
 * 订单状态值对象
 */
@Getter
public enum OrderStatus {
    
    CREATED("CREATED", "已创建"),
    CONFIRMED("CONFIRMED", "已确认"),
    PAID("PAID", "已支付"),
    SHIPPED("SHIPPED", "已发货"),
    COMPLETED("COMPLETED", "已完成"),
    CANCELLED("CANCELLED", "已取消");
    
    private final String code;
    private final String description;
    
    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据代码获取状态
     */
    public static OrderStatus fromCode(String code) {
        for (OrderStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的订单状态代码: " + code);
    }
    
    /**
     * 检查是否可以转换到目标状态
     */
    public boolean canTransitionTo(OrderStatus targetStatus) {
        if (this == CREATED) {
            return targetStatus == CONFIRMED || targetStatus == CANCELLED;
        } else if (this == CONFIRMED) {
            return targetStatus == PAID || targetStatus == CANCELLED;
        } else if (this == PAID) {
            return targetStatus == SHIPPED || targetStatus == CANCELLED;
        } else if (this == SHIPPED) {
            return targetStatus == COMPLETED;
        }
        return false;
    }
    
    /**
     * 检查是否是终态
     */
    public boolean isFinal() {
        return this == COMPLETED || this == CANCELLED;
    }
    
    @Override
    public String toString() {
        return code;
    }
}
