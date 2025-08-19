package com.zhao.order.domain.model.entity;

import com.zhao.order.domain.model.valueobject.Money;
import com.zhao.order.domain.model.valueobject.ProductId;
import lombok.Getter;

import java.util.Objects;

/**
 * 订单项实体
 */
@Getter
public class OrderItem {
    
    private Long id;
    private ProductId productId;
    private String productName;
    private String productImage;
    private Money unitPrice;
    private Integer quantity;
    private Money subtotal;
    
    // 私有构造函数，强制使用工厂方法创建
    private OrderItem() {}
    
    /**
     * 创建订单项
     */
    public static OrderItem create(ProductId productId, String productName, String productImage, 
                                 Money unitPrice, Integer quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("商品名称不能为空");
        }
        if (unitPrice == null || unitPrice.isZero()) {
            throw new IllegalArgumentException("商品单价不能为空或为零");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("商品数量必须大于0");
        }
        
        OrderItem orderItem = new OrderItem();
        orderItem.productId = productId;
        orderItem.productName = productName;
        orderItem.productImage = productImage;
        orderItem.unitPrice = unitPrice;
        orderItem.quantity = quantity;
        orderItem.subtotal = unitPrice.multiply(quantity);
        
        return orderItem;
    }
    
    /**
     * 设置ID（用于持久化）
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 更新数量
     */
    public void updateQuantity(Integer newQuantity) {
        if (newQuantity == null || newQuantity <= 0) {
            throw new IllegalArgumentException("商品数量必须大于0");
        }
        this.quantity = newQuantity;
        this.subtotal = this.unitPrice.multiply(newQuantity);
    }
    
    /**
     * 更新单价
     */
    public void updateUnitPrice(Money newUnitPrice) {
        if (newUnitPrice == null || newUnitPrice.isZero()) {
            throw new IllegalArgumentException("商品单价不能为空或为零");
        }
        this.unitPrice = newUnitPrice;
        this.subtotal = newUnitPrice.multiply(this.quantity);
    }
    
    /**
     * 获取商品总价
     */
    public Money getSubtotal() {
        return subtotal;
    }
    
    /**
     * 检查是否是同一个商品
     */
    public boolean isSameProduct(OrderItem other) {
        return this.productId.equals(other.productId);
    }
    
    /**
     * 合并相同商品的订单项
     */
    public void merge(OrderItem other) {
        if (!isSameProduct(other)) {
            throw new IllegalArgumentException("只能合并相同商品的订单项");
        }
        this.quantity += other.quantity;
        this.subtotal = this.unitPrice.multiply(this.quantity);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productId, orderItem.productId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
    
    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
