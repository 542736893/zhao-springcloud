package com.zhao.order.domain.service;

import com.zhao.order.domain.model.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderDomainService {
    
    /**
     * 验证订单是否可以创建
     */
    public boolean canCreateOrder(Order order) {
        return order != null 
            && order.getUserId() != null 
            && order.getProductId() != null 
            && order.getCount() != null 
            && order.getCount() > 0 
            && order.getAmount() != null 
            && order.getAmount().compareTo(java.math.BigDecimal.ZERO) > 0;
    }
    
    /**
     * 验证订单状态是否可以更新
     */
    public boolean canUpdateOrder(Order order) {
        return order != null && "DRAFT".equals(order.getStatus());
    }
    
    /**
     * 计算订单总金额
     */
    public java.math.BigDecimal calculateOrderAmount(java.math.BigDecimal unitPrice, Integer count) {
        if (unitPrice == null || count == null || count <= 0) {
            return java.math.BigDecimal.ZERO;
        }
        return unitPrice.multiply(new java.math.BigDecimal(count));
    }
}
