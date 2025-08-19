package com.zhao.order.domain.factory;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.*;
import com.zhao.order.application.command.CreateOrderCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单工厂
 */
@Component
public class OrderFactory {
    
    /**
     * 从创建订单命令创建订单
     */
    public static Order createFromCommand(CreateOrderCommand command) {
        // 创建订单号
        OrderNumber orderNumber = OrderNumber.generate();
        
        // 创建地址
        Address shippingAddress = Address.of(
                command.getProvince(),
                command.getCity(),
                command.getDistrict(),
                command.getDetailAddress(),
                command.getReceiverName(),
                command.getReceiverPhone(),
                command.getZipCode()
        );
        
        // 创建订单
        Order order = Order.create(
                UserId.of(command.getUserId()),
                orderNumber,
                shippingAddress
        );
        
        // 添加订单项
        List<OrderItem> orderItems = command.getItems().stream()
                .map(item -> OrderItem.create(
                        ProductId.of(item.getProductId()),
                        item.getProductName(),
                        item.getProductImage(),
                        Money.of(item.getUnitPrice()),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
        
        orderItems.forEach(order::addOrderItem);
        
        return order;
    }
    
    /**
     * 创建订单项
     */
    public static OrderItem createOrderItem(Long productId, String productName, 
                                         String productImage, String unitPrice, Integer quantity) {
        return OrderItem.create(
                ProductId.of(productId),
                productName,
                productImage,
                Money.of(unitPrice),
                quantity
        );
    }
    
    /**
     * 创建地址
     */
    public static Address createAddress(String province, String city, String district,
                                     String detailAddress, String receiverName, 
                                     String receiverPhone, String zipCode) {
        return Address.of(province, city, district, detailAddress, receiverName, receiverPhone, zipCode);
    }
}
