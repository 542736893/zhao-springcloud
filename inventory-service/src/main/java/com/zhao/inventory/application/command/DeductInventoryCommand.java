package com.zhao.inventory.application.command;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 扣减库存命令
 */
@Data
public class DeductInventoryCommand {
    
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    @Positive(message = "商品ID必须大于0")
    private Long productId;
    
    /**
     * 扣减数量
     */
    @NotNull(message = "扣减数量不能为空")
    @Positive(message = "扣减数量必须大于0")
    private Integer quantity;
    
    /**
     * 仓库ID（可选）
     */
    private Long warehouseId;
    
    /**
     * 操作原因
     */
    private String reason;
    
    public DeductInventoryCommand() {}
    
    public DeductInventoryCommand(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public DeductInventoryCommand(Long productId, Integer quantity, Long warehouseId) {
        this.productId = productId;
        this.quantity = quantity;
        this.warehouseId = warehouseId;
    }
    
    public DeductInventoryCommand(Long productId, Integer quantity, Long warehouseId, String reason) {
        this.productId = productId;
        this.quantity = quantity;
        this.warehouseId = warehouseId;
        this.reason = reason;
    }
}
