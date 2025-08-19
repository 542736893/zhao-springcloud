package com.zhao.order.interfaces.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单响应DTO
 */
@Data
public class OrderResponse {
    
    private Long id;
    private String orderNumber;
    private Long userId;
    private String status;
    private String statusDescription;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal actualAmount;
    private AddressResponse shippingAddress;
    private List<OrderItemResponse> orderItems;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    /**
     * 地址响应DTO
     */
    @Data
    public static class AddressResponse {
        private String province;
        private String city;
        private String district;
        private String detailAddress;
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String fullAddress;
    }
    
    /**
     * 订单项响应DTO
     */
    @Data
    public static class OrderItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private String productImage;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}
