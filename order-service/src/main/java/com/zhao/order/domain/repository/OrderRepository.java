package com.zhao.order.domain.repository;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.valueobject.OrderId;
import com.zhao.order.domain.model.valueobject.OrderNumber;
import com.zhao.order.domain.model.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * 订单仓储接口
 */
public interface OrderRepository {
    
    /**
     * 保存订单
     */
    Order save(Order order);

    /**
     * 简单保存订单
     */
    Order simpleSave(Order order);

    /**
     * 根据ID查找订单
     */
    Optional<Order> findById(OrderId orderId);
    
    /**
     * 根据订单号查找订单
     */
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);
    
    /**
     * 根据用户ID查找订单列表
     */
    List<Order> findByUserId(UserId userId);
    
    /**
     * 根据用户ID和状态查找订单列表
     */
    List<Order> findByUserIdAndStatus(UserId userId, String status);
    
    /**
     * 根据状态查找订单列表
     */
    List<Order> findByStatus(String status);
    
    /**
     * 检查订单号是否存在
     */
    boolean existsByOrderNumber(OrderNumber orderNumber);
    
    /**
     * 删除订单
     */
    void delete(OrderId orderId);
    
    /**
     * 统计用户订单数量
     */
    long countByUserId(UserId userId);
    
    /**
     * 统计指定状态的订单数量
     */
    long countByStatus(String status);
}


