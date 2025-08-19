package com.zhao.inventory.interfaces.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 扣减库存请求
 */
@Data
@Schema(description = "扣减库存请求")
public class DeductInventoryRequest {
    
    @Schema(description = "商品ID", example = "1001")
    @NotNull(message = "商品ID不能为空")
    @Positive(message = "商品ID必须大于0")
    private Long productId;
    
    @Schema(description = "扣减数量", example = "10")
    @NotNull(message = "扣减数量不能为空")
    @Positive(message = "扣减数量必须大于0")
    private Integer quantity;
    
    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;
    
    @Schema(description = "操作原因", example = "订单扣减")
    private String reason;
}
