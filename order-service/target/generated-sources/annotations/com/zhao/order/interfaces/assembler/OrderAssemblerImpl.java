package com.zhao.order.interfaces.assembler;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.Address;
import com.zhao.order.domain.model.valueobject.Money;
import com.zhao.order.domain.model.valueobject.OrderId;
import com.zhao.order.domain.model.valueobject.OrderNumber;
import com.zhao.order.domain.model.valueobject.OrderStatus;
import com.zhao.order.domain.model.valueobject.UserId;
import com.zhao.order.interfaces.dto.response.OrderResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-19T18:59:36+0800",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class OrderAssemblerImpl implements OrderAssembler {

    @Override
    public OrderResponse toResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setId( orderIdValue( order ) );
        orderResponse.setOrderNumber( orderOrderNumberValue( order ) );
        orderResponse.setUserId( orderUserIdValue( order ) );
        orderResponse.setStatus( orderStatusCode( order ) );
        orderResponse.setStatusDescription( orderStatusDescription( order ) );
        orderResponse.setTotalAmount( orderTotalAmountAmount( order ) );
        orderResponse.setDiscountAmount( orderDiscountAmountAmount( order ) );
        orderResponse.setActualAmount( orderActualAmountAmount( order ) );
        orderResponse.setShippingAddress( toAddressResponse( order.getShippingAddress() ) );
        orderResponse.setOrderItems( toOrderItemResponseList( order.getOrderItems() ) );
        orderResponse.setCreateTime( order.getCreateTime() );
        orderResponse.setUpdateTime( order.getUpdateTime() );

        return orderResponse;
    }

    @Override
    public OrderResponse.OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderResponse.OrderItemResponse orderItemResponse = new OrderResponse.OrderItemResponse();

        orderItemResponse.setProductId( productIdToLong( orderItem.getProductId() ) );
        orderItemResponse.setUnitPrice( orderItemUnitPriceAmount( orderItem ) );
        orderItemResponse.setSubtotal( orderItemSubtotalAmount( orderItem ) );
        orderItemResponse.setId( orderItem.getId() );
        orderItemResponse.setProductName( orderItem.getProductName() );
        orderItemResponse.setProductImage( orderItem.getProductImage() );
        orderItemResponse.setQuantity( orderItem.getQuantity() );

        return orderItemResponse;
    }

    @Override
    public List<OrderResponse.OrderItemResponse> toOrderItemResponseList(List<OrderItem> orderItems) {
        if ( orderItems == null ) {
            return null;
        }

        List<OrderResponse.OrderItemResponse> list = new ArrayList<OrderResponse.OrderItemResponse>( orderItems.size() );
        for ( OrderItem orderItem : orderItems ) {
            list.add( toOrderItemResponse( orderItem ) );
        }

        return list;
    }

    @Override
    public OrderResponse.AddressResponse toAddressResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        OrderResponse.AddressResponse addressResponse = new OrderResponse.AddressResponse();

        addressResponse.setFullAddress( address.getFullAddress() );
        addressResponse.setProvince( address.getProvince() );
        addressResponse.setCity( address.getCity() );
        addressResponse.setDistrict( address.getDistrict() );
        addressResponse.setDetailAddress( address.getDetailAddress() );
        addressResponse.setReceiverName( address.getReceiverName() );
        addressResponse.setReceiverPhone( address.getReceiverPhone() );
        addressResponse.setZipCode( address.getZipCode() );

        return addressResponse;
    }

    private Long orderIdValue(Order order) {
        OrderId id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id.getValue();
    }

    private String orderOrderNumberValue(Order order) {
        OrderNumber orderNumber = order.getOrderNumber();
        if ( orderNumber == null ) {
            return null;
        }
        return orderNumber.getValue();
    }

    private Long orderUserIdValue(Order order) {
        UserId userId = order.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId.getValue();
    }

    private String orderStatusCode(Order order) {
        OrderStatus status = order.getStatus();
        if ( status == null ) {
            return null;
        }
        return status.getCode();
    }

    private String orderStatusDescription(Order order) {
        OrderStatus status = order.getStatus();
        if ( status == null ) {
            return null;
        }
        return status.getDescription();
    }

    private BigDecimal orderTotalAmountAmount(Order order) {
        Money totalAmount = order.getTotalAmount();
        if ( totalAmount == null ) {
            return null;
        }
        return totalAmount.getAmount();
    }

    private BigDecimal orderDiscountAmountAmount(Order order) {
        Money discountAmount = order.getDiscountAmount();
        if ( discountAmount == null ) {
            return null;
        }
        return discountAmount.getAmount();
    }

    private BigDecimal orderActualAmountAmount(Order order) {
        Money actualAmount = order.getActualAmount();
        if ( actualAmount == null ) {
            return null;
        }
        return actualAmount.getAmount();
    }

    private BigDecimal orderItemUnitPriceAmount(OrderItem orderItem) {
        Money unitPrice = orderItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        return unitPrice.getAmount();
    }

    private BigDecimal orderItemSubtotalAmount(OrderItem orderItem) {
        Money subtotal = orderItem.getSubtotal();
        if ( subtotal == null ) {
            return null;
        }
        return subtotal.getAmount();
    }
}
