package com.zhao.inventory.domain.repository;

import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.model.valueobject.InventoryId;
import com.zhao.inventory.domain.model.valueobject.ProductId;
import com.zhao.inventory.domain.model.valueobject.WarehouseId;

import java.util.List;
import java.util.Optional;

/**
 * 库存仓储接口
 */
public interface InventoryRepository {
    
    /**
     * 保存库存
     */
    Inventory save(Inventory inventory);
    
    /**
     * 根据ID查找库存
     */
    Optional<Inventory> findById(InventoryId id);
    
    /**
     * 根据商品ID查找库存
     */
    Optional<Inventory> findByProductId(ProductId productId);
    
    /**
     * 根据商品ID和仓库ID查找库存
     */
    Optional<Inventory> findByProductIdAndWarehouseId(ProductId productId, WarehouseId warehouseId);
    
    /**
     * 根据仓库ID查找所有库存
     */
    List<Inventory> findByWarehouseId(WarehouseId warehouseId);
    
    /**
     * 查找低库存商品
     */
    List<Inventory> findLowStockInventories();
    
    /**
     * 查找缺货商品
     */
    List<Inventory> findOutOfStockInventories();
    
    /**
     * 删除库存
     */
    void delete(InventoryId id);
    
    /**
     * 检查库存是否存在
     */
    boolean existsById(InventoryId id);
    
    /**
     * 检查商品库存是否存在
     */
    boolean existsByProductId(ProductId productId);
}
