package com.zhao.order.infrastructure.persistence.repository;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.*;
import com.zhao.order.domain.repository.OrderRepository;
import com.zhao.order.infrastructure.persistence.mapper.OrderItemMapper;
import com.zhao.order.infrastructure.persistence.mapper.OrderMapper;
import com.zhao.order.infrastructure.persistence.po.OrderDO;
import com.zhao.order.infrastructure.persistence.po.OrderItemDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 订单仓储实现类
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    
    @Override
    @Transactional
    public Order save(Order order) {
        if (order.getId() == null) {
            // 新增订单
            OrderDO orderDO = convertToOrderDO(order);
            orderMapper.insert(orderDO);
            
            // 设置订单ID
            order.assignId(orderDO.getId());
            
            // 保存订单项
            if (!order.getOrderItems().isEmpty()) {
                List<OrderItemDO> orderItemDOs = convertToOrderItemDOList(order.getOrderItems(), orderDO.getId());
                orderItemMapper.batchInsert(orderItemDOs);
            }
        } else {
            // 更新订单
            OrderDO orderDO = convertToOrderDO(order);
            orderMapper.updateById(orderDO);
            
            // 更新订单项
            if (!order.getOrderItems().isEmpty()) {
                // 先删除原有订单项
                orderItemMapper.deleteByOrderId(orderDO.getId());
                // 再插入新的订单项
                List<OrderItemDO> orderItemDOs = convertToOrderItemDOList(order.getOrderItems(), orderDO.getId());
                orderItemMapper.batchInsert(orderItemDOs);
            }
        }
        
        return order;
    }

    @Override
    public Order simpleSave(Order order) {
        if (order != null) {
            OrderDO orderDO = convertToOrderDO(order);
            orderMapper.insert(orderDO);
            // 回填数据库生成的ID
            order.assignId(orderDO.getId());
        }
        return order;
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId.getValue());
        if (orderDO == null) {
            return Optional.empty();
        }
        
        // 查询订单项
        List<OrderItemDO> orderItemDOs = orderItemMapper.selectByOrderId(orderDO.getId());
        
        // 转换为领域对象
        Order order = convertToOrder(orderDO, orderItemDOs);
        return Optional.of(order);
    }
    
    @Override
    public Optional<Order> findByOrderNumber(OrderNumber orderNumber) {
        OrderDO orderDO = orderMapper.selectByOrderNumber(orderNumber.getValue());
        if (orderDO == null) {
            return Optional.empty();
        }
        
        // 查询订单项
        List<OrderItemDO> orderItemDOs = orderItemMapper.selectByOrderId(orderDO.getId());
        
        // 转换为领域对象
        Order order = convertToOrder(orderDO, orderItemDOs);
        return Optional.of(order);
    }
    
    @Override
    public List<Order> findByUserId(UserId userId) {
        List<OrderDO> orderDOs = orderMapper.selectByUserId(userId.getValue());
        return orderDOs.stream()
                .map(orderDO -> {
                    List<OrderItemDO> orderItemDOs = orderItemMapper.selectByOrderId(orderDO.getId());
                    return convertToOrder(orderDO, orderItemDOs);
                })
                .toList();
    }
    
    @Override
    public List<Order> findByUserIdAndStatus(UserId userId, String status) {
        List<OrderDO> orderDOs = orderMapper.selectByUserIdAndStatus(userId.getValue(), status);
        return orderDOs.stream()
                .map(orderDO -> {
                    List<OrderItemDO> orderItemDOs = orderItemMapper.selectByOrderId(orderDO.getId());
                    return convertToOrder(orderDO, orderItemDOs);
                })
                .toList();
    }
    
    @Override
    public List<Order> findByStatus(String status) {
        List<OrderDO> orderDOs = orderMapper.selectByStatus(status);
        return orderDOs.stream()
                .map(orderDO -> {
                    List<OrderItemDO> orderItemDOs = orderItemMapper.selectByOrderId(orderDO.getId());
                    return convertToOrder(orderDO, orderItemDOs);
                })
                .toList();
    }
    
    @Override
    public boolean existsByOrderNumber(OrderNumber orderNumber) {
        return orderMapper.countByOrderNumber(orderNumber.getValue()) > 0;
    }
    
    @Override
    @Transactional
    public void delete(OrderId orderId) {
        // 删除订单项
        orderItemMapper.deleteByOrderId(orderId.getValue());
        // 删除订单
        orderMapper.deleteById(orderId.getValue());
    }
    
    @Override
    public long countByUserId(UserId userId) {
        return orderMapper.countByUserId(userId.getValue());
    }
    
    @Override
    public long countByStatus(String status) {
        return orderMapper.countByStatus(status);
    }
    
    /**
     * 转换为OrderDO
     */
    private OrderDO convertToOrderDO(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(order.getId() != null ? order.getId().getValue() : null);
        orderDO.setOrderNumber(order.getOrderNumber().getValue());
        orderDO.setUserId(order.getUserId().getValue());
        orderDO.setStatus(order.getStatus().getCode());
        orderDO.setTotalAmount(order.getTotalAmount().getAmount());
        orderDO.setDiscountAmount(order.getDiscountAmount().getAmount());
        orderDO.setActualAmount(order.getActualAmount().getAmount());
        orderDO.setProvince(order.getShippingAddress().getProvince());
        orderDO.setCity(order.getShippingAddress().getCity());
        orderDO.setDistrict(order.getShippingAddress().getDistrict());
        orderDO.setDetailAddress(order.getShippingAddress().getDetailAddress());
        orderDO.setReceiverName(order.getShippingAddress().getReceiverName());
        orderDO.setReceiverPhone(order.getShippingAddress().getReceiverPhone());
        orderDO.setZipCode(order.getShippingAddress().getZipCode());
        orderDO.setCreateTime(order.getCreateTime());
        orderDO.setUpdateTime(order.getUpdateTime());
        return orderDO;
    }
    
    /**
     * 转换为Order
     */
    private Order convertToOrder(OrderDO orderDO, List<OrderItemDO> orderItemDOs) {
        // 创建地址
        Address address = Address.of(
                orderDO.getProvince(),
                orderDO.getCity(),
                orderDO.getDistrict(),
                orderDO.getDetailAddress(),
                orderDO.getReceiverName(),
                orderDO.getReceiverPhone(),
                orderDO.getZipCode()
        );
        
        // 创建订单
        Order order = Order.create(
                UserId.of(orderDO.getUserId()),
                OrderNumber.of(orderDO.getOrderNumber()),
                address
        );
        
        // 设置ID和时间
        order.assignId(orderDO.getId());
        
        // 添加订单项
        orderItemDOs.forEach(itemDO -> {
            OrderItem orderItem = OrderItem.create(
                    ProductId.of(itemDO.getProductId()),
                    itemDO.getProductName(),
                    itemDO.getProductImage(),
                    Money.of(itemDO.getUnitPrice()),
                    itemDO.getQuantity()
            );
            orderItem.setId(itemDO.getId());
            order.addOrderItem(orderItem);
        });
        
        return order;
    }
    
    /**
     * 转换为OrderItemDO列表
     */
    private List<OrderItemDO> convertToOrderItemDOList(List<OrderItem> orderItems, Long orderId) {
        return orderItems.stream()
                .map(item -> {
                    OrderItemDO itemDO = new OrderItemDO();
                    itemDO.setOrderId(orderId);
                    itemDO.setProductId(item.getProductId().getValue());
                    itemDO.setProductName(item.getProductName());
                    itemDO.setProductImage(item.getProductImage());
                    itemDO.setUnitPrice(item.getUnitPrice().getAmount());
                    itemDO.setQuantity(item.getQuantity());
                    itemDO.setSubtotal(item.getSubtotal().getAmount());
                    return itemDO;
                })
                .toList();
    }
}
