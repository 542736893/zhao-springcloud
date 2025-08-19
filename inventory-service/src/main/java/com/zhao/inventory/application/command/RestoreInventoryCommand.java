package com.zhao.inventory.application.command;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 恢复库存命令
 */
@Data
public class RestoreInventoryCommand {
    
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    @Positive(message = "商品ID必须大于0")
    private Long productId;
    
    /**
     * 恢复数量
     */
    @NotNull(message = "恢复数量不能为空")
    @Positive(message = "恢复数量必须大于0")
    private Integer quantity;
    
    /**
     * 仓库ID（可选）
     */
    private Long warehouseId;
    
    /**
     * 操作原因
     */
    private String reason;
    
    public RestoreInventoryCommand() {}
    
    public RestoreInventoryCommand(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public RestoreInventoryCommand(Long productId, Integer quantity, String reason) {
        this.productId = productId;
        this.quantity = quantity;
        this.reason = reason;
    }
}
