package com.zhao.inventory.domain.model.aggregate;

import com.zhao.inventory.domain.model.valueobject.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 库存聚合根
 */
@Getter
public class Inventory {
    
    private InventoryId id;
    private ProductId productId;
    private WarehouseId warehouseId;
    private Quantity availableQuantity;
    private Quantity reservedQuantity;
    private Quantity totalQuantity;
    private InventoryStatus status;
    private Unit unit;
    private Location location;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 私有构造函数，强制使用工厂方法创建
    private Inventory() {}
    
    /**
     * 创建库存
     */
    public static Inventory create(ProductId productId, WarehouseId warehouseId,
                                 Quantity initialQuantity, Unit unit, Location location) {
        Inventory inventory = new Inventory();
        inventory.id = InventoryId.generate();
        inventory.productId = productId;
        inventory.warehouseId = warehouseId;
        inventory.availableQuantity = initialQuantity;
        inventory.reservedQuantity = Quantity.ZERO;
        inventory.totalQuantity = initialQuantity;
        inventory.status = InventoryStatus.IN_STOCK;
        inventory.unit = unit;
        inventory.location = location;
        inventory.createTime = LocalDateTime.now();
        inventory.updateTime = LocalDateTime.now();
        return inventory;
    }

    /**
     * 重建库存（从持久化数据）
     */
    public static Inventory rebuild(InventoryId id, ProductId productId, WarehouseId warehouseId,
                                  Quantity availableQuantity, Quantity reservedQuantity, Quantity totalQuantity,
                                  InventoryStatus status, Unit unit, Location location,
                                  LocalDateTime createTime, LocalDateTime updateTime) {
        Inventory inventory = new Inventory();
        inventory.id = id;
        inventory.productId = productId;
        inventory.warehouseId = warehouseId;
        inventory.availableQuantity = availableQuantity;
        inventory.reservedQuantity = reservedQuantity;
        inventory.totalQuantity = totalQuantity;
        inventory.status = status;
        inventory.unit = unit;
        inventory.location = location;
        inventory.createTime = createTime;
        inventory.updateTime = updateTime;
        return inventory;
    }
    
    /**
     * 扣减库存
     */
    public void deduct(Quantity quantity) {
        if (quantity == null || quantity.isZero()) {
            throw new IllegalArgumentException("扣减数量不能为空或为零");
        }
        
        if (availableQuantity.isLessThan(quantity)) {
            throw new IllegalStateException("可用库存不足，当前可用: " + availableQuantity + 
                    ", 需要扣减: " + quantity);
        }
        
        this.availableQuantity = this.availableQuantity.subtract(quantity);
        this.totalQuantity = this.totalQuantity.subtract(quantity);
        this.updateTime = LocalDateTime.now();
        
        // 检查库存状态
        updateInventoryStatus();
    }
    
    /**
     * 恢复库存
     */
    public void restore(Quantity quantity) {
        if (quantity == null || quantity.isZero()) {
            throw new IllegalArgumentException("恢复数量不能为空或为零");
        }
        
        this.availableQuantity = this.availableQuantity.add(quantity);
        this.totalQuantity = this.totalQuantity.add(quantity);
        this.updateTime = LocalDateTime.now();
        
        // 检查库存状态
        updateInventoryStatus();
    }
    
    /**
     * 预占库存
     */
    public void reserve(Quantity quantity) {
        if (quantity == null || quantity.isZero()) {
            throw new IllegalArgumentException("预占数量不能为空或为零");
        }
        
        if (availableQuantity.isLessThan(quantity)) {
            throw new IllegalStateException("可用库存不足，无法预占");
        }
        
        this.availableQuantity = this.availableQuantity.subtract(quantity);
        this.reservedQuantity = this.reservedQuantity.add(quantity);
        this.updateTime = LocalDateTime.now();
        
        // 检查库存状态
        updateInventoryStatus();
    }
    
    /**
     * 释放预占库存
     */
    public void releaseReservation(Quantity quantity) {
        if (quantity == null || quantity.isZero()) {
            throw new IllegalArgumentException("释放数量不能为空或为零");
        }
        
        if (reservedQuantity.isLessThan(quantity)) {
            throw new IllegalStateException("预占库存不足，无法释放");
        }
        
        this.availableQuantity = this.availableQuantity.add(quantity);
        this.reservedQuantity = this.reservedQuantity.subtract(quantity);
        this.updateTime = LocalDateTime.now();
        
        // 检查库存状态
        updateInventoryStatus();
    }
    
    /**
     * 调整库存
     */
    public void adjust(Quantity newTotalQuantity) {
        if (newTotalQuantity == null) {
            throw new IllegalArgumentException("新库存数量不能为空");
        }
        
        if (newTotalQuantity.isNegative()) {
            throw new IllegalArgumentException("库存数量不能为负数");
        }
        
        // 计算差值
        Quantity difference = newTotalQuantity.subtract(this.totalQuantity);
        
        if (difference.isPositive()) {
            // 增加库存
            this.availableQuantity = this.availableQuantity.add(difference);
            this.totalQuantity = newTotalQuantity;
        } else if (difference.isNegative()) {
            // 减少库存
            Quantity absDifference = difference.abs();
            if (this.availableQuantity.isLessThan(absDifference)) {
                throw new IllegalStateException("可用库存不足，无法调整");
            }
            this.availableQuantity = this.availableQuantity.subtract(absDifference);
            this.totalQuantity = newTotalQuantity;
        }
        
        this.updateTime = LocalDateTime.now();
        
        // 检查库存状态
        updateInventoryStatus();
    }
    
    /**
     * 更新库存状态
     */
    private void updateInventoryStatus() {
        if (this.totalQuantity.isZero()) {
            this.status = InventoryStatus.OUT_OF_STOCK;
        } else if (this.availableQuantity.isZero()) {
            this.status = InventoryStatus.RESERVED;
        } else if (this.availableQuantity.isLessThan(Quantity.of(10))) {
            this.status = InventoryStatus.LOW_STOCK;
        } else {
            this.status = InventoryStatus.IN_STOCK;
        }
    }
    
    /**
     * 检查是否有足够库存
     */
    public boolean hasEnoughStock(Quantity requiredQuantity) {
        return availableQuantity.isGreaterThanOrEqualTo(requiredQuantity);
    }
    
    /**
     * 检查是否缺货
     */
    public boolean isOutOfStock() {
        return this.status == InventoryStatus.OUT_OF_STOCK;
    }
    
    /**
     * 检查是否库存不足
     */
    public boolean isLowStock() {
        return this.status == InventoryStatus.LOW_STOCK;
    }
    
    /**
     * 获取可用库存百分比
     */
    public double getAvailableStockPercentage() {
        if (totalQuantity.isZero()) {
            return 0.0;
        }
        return availableQuantity.getValue().doubleValue() / totalQuantity.getValue().doubleValue() * 100;
    }
    
    /**
     * 设置ID（用于持久化）
     */
    public void setId(InventoryId id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", productId=" + productId +
                ", warehouseId=" + warehouseId +
                ", availableQuantity=" + availableQuantity +
                ", totalQuantity=" + totalQuantity +
                ", status=" + status +
                '}';
    }
}
