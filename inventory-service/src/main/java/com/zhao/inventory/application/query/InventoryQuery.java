package com.zhao.inventory.application.query;

import lombok.Data;

/**
 * 库存查询条件
 */
@Data
public class InventoryQuery {
    
    /**
     * 商品ID
     */
    private Long productId;
    
    /**
     * 仓库ID
     */
    private Long warehouseId;
    
    /**
     * 库存状态
     */
    private String status;
    
    /**
     * 最小库存数量
     */
    private Integer minQuantity;
    
    /**
     * 最大库存数量
     */
    private Integer maxQuantity;
    
    /**
     * 是否只查询低库存
     */
    private Boolean lowStockOnly;
    
    /**
     * 是否只查询缺货
     */
    private Boolean outOfStockOnly;
    
    public InventoryQuery() {}
    
    public InventoryQuery(Long productId) {
        this.productId = productId;
    }
    
    public InventoryQuery(Long productId, Long warehouseId) {
        this.productId = productId;
        this.warehouseId = warehouseId;
    }
}
