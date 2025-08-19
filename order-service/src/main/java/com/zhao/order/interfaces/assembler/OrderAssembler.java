package com.zhao.order.interfaces.assembler;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.OrderStatus;
import com.zhao.order.domain.model.valueobject.ProductId;
import com.zhao.order.interfaces.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * 订单装配器
 */
@Mapper(componentModel = "spring")
public interface OrderAssembler {
    
    /**
     * 订单聚合根转换为响应DTO
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "orderNumber", source = "orderNumber.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "statusDescription", source = "status.description")
    @Mapping(target = "totalAmount", source = "totalAmount.amount")
    @Mapping(target = "discountAmount", source = "discountAmount.amount")
    @Mapping(target = "actualAmount", source = "actualAmount.amount")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderResponse toResponse(Order order);
    
    /**
     * 订单项实体转换为响应DTO
     */
    @Mapping(target = "productId", source = "productId", qualifiedByName = "productIdToLong")
    @Mapping(target = "unitPrice", source = "unitPrice.amount")
    @Mapping(target = "subtotal", source = "subtotal.amount")
    OrderResponse.OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    
    /**
     * 订单项列表转换
     */
    List<OrderResponse.OrderItemResponse> toOrderItemResponseList(List<OrderItem> orderItems);
    
    /**
     * 地址值对象转换为响应DTO
     */
    @Mapping(target = "fullAddress", source = "fullAddress")
    OrderResponse.AddressResponse toAddressResponse(com.zhao.order.domain.model.valueobject.Address address);
    
    /**
     * 订单状态转换为描述
     */
    @Named("statusToDescription")
    default String statusToDescription(OrderStatus status) {
        return status != null ? status.getDescription() : "";
    }
    
    /**
     * ProductId转换为Long
     */
    @Named("productIdToLong")
    default Long productIdToLong(ProductId productId) {
        return productId != null ? productId.getValue() : null;
    }
}
