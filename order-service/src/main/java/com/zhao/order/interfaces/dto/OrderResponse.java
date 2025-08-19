package com.zhao.order.interfaces.dto;

import com.zhao.order.domain.model.order.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 构造函数
    public OrderResponse() {}
    
    public OrderResponse(Long id, Long userId, Long productId, Integer count, 
                        BigDecimal amount, String status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.amount = amount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    // 从Order实体创建OrderResponse的静态方法
    public static OrderResponse fromOrder(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setProductId(order.getProductId());
        response.setCount(order.getCount());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        response.setCreateTime(order.getCreateTime());
        response.setUpdateTime(null); // Order实体没有updateTime字段
        return response;
    }
    
    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}
