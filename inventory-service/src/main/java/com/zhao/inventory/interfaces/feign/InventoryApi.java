package com.zhao.inventory.interfaces.feign;

import com.zhao.common.web.ApiResponse;
import com.zhao.inventory.interfaces.dto.response.InventoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存对外API接口
 * 供其他服务通过Feign调用
 */
@Tag(name = "库存API", description = "库存服务对外API")
public interface InventoryApi {
    
    /**
     * 扣减库存
     */
    @Operation(summary = "扣减库存", description = "扣减指定商品的库存数量")
    @PostMapping("/api/v1/inventory/deduct")
    ApiResponse<Void> deductInventory(
            @Parameter(description = "商品ID", required = true, example = "1001")
            @RequestParam("productId") Long productId,
            @Parameter(description = "扣减数量", required = true, example = "10")
            @RequestParam("quantity") Integer quantity
    );

    /**
     * 恢复库存
     */
    @Operation(summary = "恢复库存", description = "恢复指定商品的库存数量")
    @PostMapping("/api/v1/inventory/restore")
    ApiResponse<Void> restoreInventory(
            @Parameter(description = "商品ID", required = true, example = "1001")
            @RequestParam("productId") Long productId,
            @Parameter(description = "恢复数量", required = true, example = "10")
            @RequestParam("quantity") Integer quantity
    );
    
    /**
     * 查询库存
     */
    @Operation(summary = "查询库存", description = "查询指定商品的库存信息")
    @GetMapping("/api/v1/inventory/product/{productId}")
    ApiResponse<InventoryResponse> getInventory(
            @Parameter(description = "商品ID", required = true, example = "1001")
            @PathVariable("productId") Long productId
    );
    
    /**
     * 检查库存是否充足
     */
    @Operation(summary = "检查库存", description = "检查指定商品的库存是否充足")
    @GetMapping("/api/v1/inventory/check")
    ApiResponse<Boolean> checkInventory(
            @Parameter(description = "商品ID", required = true, example = "1001")
            @RequestParam("productId") Long productId,
            @Parameter(description = "需要数量", required = true, example = "10")
            @RequestParam("quantity") Integer quantity
    );
}
