package com.zhao.order.infrastructure.persistence.converter;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.*;
import com.zhao.order.infrastructure.persistence.po.OrderDO;
import com.zhao.order.infrastructure.persistence.po.OrderItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * 订单PO转换器
 */
@Mapper(componentModel = "spring")
public interface OrderPOConverter {
    
    /**
     * 订单聚合根转换为OrderDO
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "orderNumber", source = "orderNumber.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "totalAmount", source = "totalAmount.amount")
    @Mapping(target = "discountAmount", source = "discountAmount.amount")
    @Mapping(target = "actualAmount", source = "actualAmount.amount")
    @Mapping(target = "province", source = "shippingAddress.province")
    @Mapping(target = "city", source = "shippingAddress.city")
    @Mapping(target = "district", source = "shippingAddress.district")
    @Mapping(target = "detailAddress", source = "shippingAddress.detailAddress")
    @Mapping(target = "receiverName", source = "shippingAddress.receiverName")
    @Mapping(target = "receiverPhone", source = "shippingAddress.receiverPhone")
    @Mapping(target = "zipCode", source = "shippingAddress.zipCode")
    @Mapping(target = "deleted", constant = "0")
    OrderDO toOrderDO(Order order);
    
    /**
     * OrderItemDO转换为订单项实体
     */
    default OrderItem toOrderItem(OrderItemDO orderItemDO) {
        if (orderItemDO == null) {
            return null;
        }

        OrderItem orderItem = OrderItem.create(
                ProductId.of(orderItemDO.getProductId()),
                orderItemDO.getProductName(),
                orderItemDO.getProductImage(),
                Money.of(orderItemDO.getUnitPrice()),
                orderItemDO.getQuantity()
        );
        orderItem.setId(orderItemDO.getId());
        return orderItem;
    }

    /**
     * 订单项实体转换为OrderItemDO
     */
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "productId", source = "orderItem.productId.value")
    @Mapping(target = "productName", source = "orderItem.productName")
    @Mapping(target = "productImage", source = "orderItem.productImage")
    @Mapping(target = "unitPrice", source = "orderItem.unitPrice.amount")
    @Mapping(target = "quantity", source = "orderItem.quantity")
    @Mapping(target = "subtotal", source = "orderItem.subtotal.amount")
    OrderItemDO toOrderItemDO(OrderItem orderItem, Long orderId);

    /**
     * 订单项列表转换
     */
    default List<OrderItemDO> toOrderItemDOList(List<OrderItem> orderItems, Long orderId) {
        if (orderItems == null) {
            return null;
        }
        return orderItems.stream()
                .map(item -> toOrderItemDO(item, orderId))
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 创建OrderId值对象
     */
    @Named("createOrderId")
    default OrderId createOrderId(Long id) {
        return id != null ? OrderId.of(id) : null;
    }
    
    /**
     * 创建OrderNumber值对象
     */
    @Named("createOrderNumber")
    default OrderNumber createOrderNumber(String orderNumber) {
        return orderNumber != null ? OrderNumber.of(orderNumber) : null;
    }
    
    /**
     * 创建UserId值对象
     */
    @Named("createUserId")
    default UserId createUserId(Long userId) {
        return userId != null ? UserId.of(userId) : null;
    }
    
    /**
     * 创建OrderStatus值对象
     */
    @Named("createOrderStatus")
    default OrderStatus createOrderStatus(String status) {
        return status != null ? OrderStatus.fromCode(status) : null;
    }
    
    /**
     * 创建Money值对象
     */
    @Named("createMoney")
    default Money createMoney(java.math.BigDecimal amount) {
        return amount != null ? Money.of(amount) : null;
    }
    
    /**
     * 创建Address值对象
     */
    @Named("createAddress")
    default Address createAddress(OrderDO orderDO) {
        if (orderDO == null) {
            return null;
        }
        return Address.of(
                orderDO.getProvince(),
                orderDO.getCity(),
                orderDO.getDistrict(),
                orderDO.getDetailAddress(),
                orderDO.getReceiverName(),
                orderDO.getReceiverPhone(),
                orderDO.getZipCode()
        );
    }
    
    /**
     * 创建ProductId值对象
     */
    @Named("createProductId")
    default ProductId createProductId(Long productId) {
        return productId != null ? ProductId.of(productId) : null;
    }
}
