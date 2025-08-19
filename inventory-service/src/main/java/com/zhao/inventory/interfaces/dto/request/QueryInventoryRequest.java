package com.zhao.inventory.interfaces.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询库存请求
 */
@Data
@Schema(description = "查询库存请求")
public class QueryInventoryRequest {
    
    @Schema(description = "商品ID", example = "1001")
    private Long productId;
    
    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;
    
    @Schema(description = "库存状态", example = "IN_STOCK")
    private String status;
    
    @Schema(description = "最小库存数量", example = "0")
    private Integer minQuantity;
    
    @Schema(description = "最大库存数量", example = "1000")
    private Integer maxQuantity;
    
    @Schema(description = "是否只查询低库存", example = "false")
    private Boolean lowStockOnly;
    
    @Schema(description = "是否只查询缺货", example = "false")
    private Boolean outOfStockOnly;
}
