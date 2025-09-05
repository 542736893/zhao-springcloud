package com.zhao.order.interfaces.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单消息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 订单号
     */
    private String orderNumber;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 消息类型
     */
    private String messageType;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 订单状态（可选）
     */
    private String orderStatus;
    
    /**
     * 订单金额（可选）
     */
    private Double orderAmount;
    
    /**
     * 消息时间戳
     */
    private LocalDateTime timestamp;
    
    /**
     * 扩展信息（JSON格式）
     */
    private String extInfo;
}
