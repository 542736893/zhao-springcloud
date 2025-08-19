package com.zhao.inventory.interfaces.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存响应
 */
@Data
@Schema(description = "库存响应")
public class InventoryResponse {
    
    @Schema(description = "库存ID", example = "1")
    private Long id;
    
    @Schema(description = "商品ID", example = "1001")
    private Long productId;
    
    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;
    
    @Schema(description = "可用数量", example = "100.00")
    private BigDecimal availableQuantity;
    
    @Schema(description = "预占数量", example = "20.00")
    private BigDecimal reservedQuantity;
    
    @Schema(description = "总数量", example = "120.00")
    private BigDecimal totalQuantity;
    
    @Schema(description = "库存状态", example = "IN_STOCK")
    private String status;
    
    @Schema(description = "单位代码", example = "PCS")
    private String unitCode;
    
    @Schema(description = "单位名称", example = "件")
    private String unitName;
    
    @Schema(description = "库位代码", example = "A-01-01-01")
    private String locationCode;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
