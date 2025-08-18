package com.zhao.order.interfaces.dto;

import java.math.BigDecimal;

public class CreateOrderRequest {
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal amount;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}


