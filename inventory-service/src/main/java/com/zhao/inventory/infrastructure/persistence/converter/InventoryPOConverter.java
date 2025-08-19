package com.zhao.inventory.infrastructure.persistence.converter;

import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.model.valueobject.*;
import com.zhao.inventory.infrastructure.persistence.po.InventoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 库存PO转换器
 */
@Mapper(componentModel = "spring")
@Component
public interface InventoryPOConverter {
    
    /**
     * 领域对象转PO
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "inventoryIdToLong")
    @Mapping(target = "productId", source = "productId", qualifiedByName = "productIdToLong")
    @Mapping(target = "total", source = "totalQuantity", qualifiedByName = "quantityToInteger")
    @Mapping(target = "used", expression = "java(calculateUsed(inventory))")
    @Mapping(target = "residue", source = "availableQuantity", qualifiedByName = "quantityToInteger")
    InventoryDO toPO(Inventory inventory);
    
    /**
     * PO转领域对象
     */
    default Inventory toDomain(InventoryDO inventoryDO) {
        if (inventoryDO == null) {
            return null;
        }

        return Inventory.rebuild(
                longToInventoryId(inventoryDO.getId()),
                longToProductId(inventoryDO.getProductId()),
                getDefaultWarehouseId(),
                integerToQuantity(inventoryDO.getResidue()),
                calculateReserved(inventoryDO),
                integerToQuantity(inventoryDO.getTotal()),
                calculateStatus(inventoryDO),
                getDefaultUnit(),
                getDefaultLocation(),
                inventoryDO.getCreateTime(),
                inventoryDO.getUpdateTime()
        );
    }
    
    // 辅助转换方法
    @Named("inventoryIdToLong")
    default Long inventoryIdToLong(InventoryId inventoryId) {
        return inventoryId != null ? inventoryId.getValue() : null;
    }
    
    @Named("longToInventoryId")
    default InventoryId longToInventoryId(Long id) {
        return id != null ? InventoryId.of(id) : null;
    }
    
    @Named("productIdToLong")
    default Long productIdToLong(ProductId productId) {
        return productId != null ? productId.getValue() : null;
    }
    
    @Named("longToProductId")
    default ProductId longToProductId(Long productId) {
        return productId != null ? ProductId.of(productId) : null;
    }
    
    @Named("quantityToInteger")
    default Integer quantityToInteger(Quantity quantity) {
        return quantity != null ? quantity.getValue().intValue() : 0;
    }
    
    @Named("integerToQuantity")
    default Quantity integerToQuantity(Integer value) {
        return value != null ? Quantity.of(value) : Quantity.ZERO;
    }
    
    // 计算已使用数量
    default Integer calculateUsed(Inventory inventory) {
        if (inventory.getTotalQuantity() == null || inventory.getAvailableQuantity() == null) {
            return 0;
        }
        return inventory.getTotalQuantity().subtract(inventory.getAvailableQuantity()).getValue().intValue();
    }
    
    // 计算预占数量
    default Quantity calculateReserved(InventoryDO inventoryDO) {
        // 从现有数据结构推算：预占 = 总数 - 已用 - 剩余
        int reserved = inventoryDO.getTotal() - inventoryDO.getUsed() - inventoryDO.getResidue();
        return Quantity.of(Math.max(0, reserved));
    }
    
    // 计算库存状态
    default InventoryStatus calculateStatus(InventoryDO inventoryDO) {
        if (inventoryDO.getResidue() <= 0) {
            return InventoryStatus.OUT_OF_STOCK;
        } else if (inventoryDO.getResidue() < 10) {
            return InventoryStatus.LOW_STOCK;
        } else {
            return InventoryStatus.IN_STOCK;
        }
    }
    
    // 默认仓库ID
    default WarehouseId getDefaultWarehouseId() {
        return WarehouseId.of(1L); // 默认仓库
    }
    
    // 默认单位
    default Unit getDefaultUnit() {
        return Unit.PIECE;
    }
    
    // 默认库位
    default Location getDefaultLocation() {
        return Location.of("A", "01", "01", "01");
    }
}
