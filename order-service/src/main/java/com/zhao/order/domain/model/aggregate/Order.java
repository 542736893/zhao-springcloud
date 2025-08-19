package com.zhao.order.domain.model.aggregate;

import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 订单聚合根
 */
@Getter
public class Order {
    
    private OrderId id;
    private UserId userId;
    private OrderNumber orderNumber;
    private OrderStatus status;
    private Money totalAmount;
    private Money discountAmount;
    private Money actualAmount;
    private Address shippingAddress;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderItem> orderItems;
    
    // 私有构造函数，强制使用工厂方法创建
    private Order() {
        this.orderItems = new ArrayList<>();
    }
    
    /**
     * 创建订单
     */
    public static Order create(UserId userId, OrderNumber orderNumber, Address shippingAddress) {
        Order order = new Order();
        order.id = OrderId.generate();
        order.userId = userId;
        order.orderNumber = orderNumber;
        order.status = OrderStatus.CREATED;
        order.shippingAddress = shippingAddress;
        order.createTime = LocalDateTime.now();
        order.updateTime = LocalDateTime.now();
        order.totalAmount = Money.ZERO;
        order.discountAmount = Money.ZERO;
        order.actualAmount = Money.ZERO;
        return order;
    }
    
    /**
     * 添加订单项
     */
    public void addOrderItem(OrderItem orderItem) {
        Objects.requireNonNull(orderItem, "订单项不能为空");
        this.orderItems.add(orderItem);
        recalculateAmount();
    }
    
    /**
     * 移除订单项
     */
    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        recalculateAmount();
    }
    
    /**
     * 计算订单金额
     */
    private void recalculateAmount() {
        this.totalAmount = this.orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money.ZERO, Money::add);
        
        this.actualAmount = this.totalAmount.subtract(this.discountAmount);
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 应用折扣
     */
    public void applyDiscount(Money discountAmount) {
        if (discountAmount.compareTo(this.totalAmount) > 0) {
            throw new IllegalArgumentException("折扣金额不能超过订单总金额");
        }
        this.discountAmount = discountAmount;
        recalculateAmount();
    }
    
    /**
     * 确认订单
     */
    public void confirm() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("只有已创建的订单才能确认");
        }
        if (this.orderItems.isEmpty()) {
            throw new IllegalStateException("订单项不能为空");
        }
        this.status = OrderStatus.CONFIRMED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 支付订单
     */
    public void pay() {
        if (this.status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("只有已确认的订单才能支付");
        }
        this.status = OrderStatus.PAID;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 发货
     */
    public void ship() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("只有已支付的订单才能发货");
        }
        this.status = OrderStatus.SHIPPED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 完成订单
     */
    public void complete() {
        if (this.status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("只有已发货的订单才能完成");
        }
        this.status = OrderStatus.COMPLETED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 取消订单
     */
    public void cancel() {
        if (this.status == OrderStatus.COMPLETED || this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("已完成或已取消的订单不能取消");
        }
        this.status = OrderStatus.CANCELLED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 检查订单是否可以取消
     */
    public boolean canCancel() {
        return this.status != OrderStatus.COMPLETED && this.status != OrderStatus.CANCELLED;
    }
    
    /**
     * 检查订单是否可以支付
     */
    public boolean canPay() {
        return this.status == OrderStatus.CONFIRMED;
    }
    
    /**
     * 检查订单是否可以发货
     */
    public boolean canShip() {
        return this.status == OrderStatus.PAID;
    }
    
    /**
     * 检查订单是否可以完成
     */
    public boolean canComplete() {
        return this.status == OrderStatus.SHIPPED;
    }
    
    /**
     * 获取订单项数量
     */
    public int getItemCount() {
        return this.orderItems.size();
    }
    
    /**
     * 检查订单是否为空
     */
    public boolean isEmpty() {
        return this.orderItems.isEmpty();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", actualAmount=" + actualAmount +
                ", itemCount=" + getItemCount() +
                '}';
    }
}
