package com.zhao.order.interfaces.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 自定义消息请求DTO
 */
@Data
@Schema(description = "自定义消息请求")
public class CustomMessageRequest {
    
    @Schema(description = "消息主题")
    @NotBlank(message = "消息主题不能为空")
    private String topic;
    
    @Schema(description = "消息标签")
    private String tag;
    
    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String content;
    
    @Schema(description = "消息键")
    private String key;
}
