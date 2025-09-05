package com.zhao.order.interfaces.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 发送消息请求DTO
 */
@Data
@Schema(description = "发送消息请求")
public class SendMessageRequest {
    
    @Schema(description = "订单ID")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;
    
    @Schema(description = "订单号")
    @NotBlank(message = "订单号不能为空")
    private String orderNumber;
    
    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String content;
    
    @Schema(description = "订单状态")
    private String orderStatus;
    
    @Schema(description = "订单金额")
    private Double orderAmount;
    
    @Schema(description = "扩展信息")
    private String extInfo;
}
