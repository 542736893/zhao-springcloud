package com.zhao.order.infrastructure.persistence.converter;

import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.entity.OrderItem;
import com.zhao.order.domain.model.valueobject.Address;
import com.zhao.order.domain.model.valueobject.Money;
import com.zhao.order.domain.model.valueobject.OrderId;
import com.zhao.order.domain.model.valueobject.OrderNumber;
import com.zhao.order.domain.model.valueobject.OrderStatus;
import com.zhao.order.domain.model.valueobject.ProductId;
import com.zhao.order.domain.model.valueobject.UserId;
import com.zhao.order.infrastructure.persistence.po.OrderDO;
import com.zhao.order.infrastructure.persistence.po.OrderItemDO;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-19T20:47:17+0800",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrderPOConverterImpl implements OrderPOConverter {

    @Override
    public OrderDO toOrderDO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDO orderDO = new OrderDO();

        orderDO.setId( orderIdValue( order ) );
        orderDO.setOrderNumber( orderOrderNumberValue( order ) );
        orderDO.setUserId( orderUserIdValue( order ) );
        orderDO.setStatus( orderStatusCode( order ) );
        orderDO.setTotalAmount( orderTotalAmountAmount( order ) );
        orderDO.setDiscountAmount( orderDiscountAmountAmount( order ) );
        orderDO.setActualAmount( orderActualAmountAmount( order ) );
        orderDO.setProvince( orderShippingAddressProvince( order ) );
        orderDO.setCity( orderShippingAddressCity( order ) );
        orderDO.setDistrict( orderShippingAddressDistrict( order ) );
        orderDO.setDetailAddress( orderShippingAddressDetailAddress( order ) );
        orderDO.setReceiverName( orderShippingAddressReceiverName( order ) );
        orderDO.setReceiverPhone( orderShippingAddressReceiverPhone( order ) );
        orderDO.setZipCode( orderShippingAddressZipCode( order ) );
        orderDO.setCreateTime( order.getCreateTime() );
        orderDO.setUpdateTime( order.getUpdateTime() );

        orderDO.setDeleted( 0 );

        return orderDO;
    }

    @Override
    public OrderItemDO toOrderItemDO(OrderItem orderItem, Long orderId) {
        if ( orderItem == null && orderId == null ) {
            return null;
        }

        OrderItemDO orderItemDO = new OrderItemDO();

        if ( orderItem != null ) {
            orderItemDO.setProductId( orderItemProductIdValue( orderItem ) );
            orderItemDO.setProductName( orderItem.getProductName() );
            orderItemDO.setProductImage( orderItem.getProductImage() );
            orderItemDO.setUnitPrice( orderItemUnitPriceAmount( orderItem ) );
            orderItemDO.setQuantity( orderItem.getQuantity() );
            orderItemDO.setSubtotal( orderItemSubtotalAmount( orderItem ) );
            orderItemDO.setId( orderItem.getId() );
        }
        orderItemDO.setOrderId( orderId );

        return orderItemDO;
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

    private String orderShippingAddressProvince(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getProvince();
    }

    private String orderShippingAddressCity(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getCity();
    }

    private String orderShippingAddressDistrict(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getDistrict();
    }

    private String orderShippingAddressDetailAddress(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getDetailAddress();
    }

    private String orderShippingAddressReceiverName(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getReceiverName();
    }

    private String orderShippingAddressReceiverPhone(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getReceiverPhone();
    }

    private String orderShippingAddressZipCode(Order order) {
        Address shippingAddress = order.getShippingAddress();
        if ( shippingAddress == null ) {
            return null;
        }
        return shippingAddress.getZipCode();
    }

    private Long orderItemProductIdValue(OrderItem orderItem) {
        ProductId productId = orderItem.getProductId();
        if ( productId == null ) {
            return null;
        }
        return productId.getValue();
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
