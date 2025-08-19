package com.zhao.inventory.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.model.valueobject.InventoryId;
import com.zhao.inventory.domain.model.valueobject.ProductId;
import com.zhao.inventory.domain.model.valueobject.WarehouseId;
import com.zhao.inventory.domain.repository.InventoryRepository;
import com.zhao.inventory.infrastructure.persistence.converter.InventoryPOConverter;
import com.zhao.inventory.infrastructure.persistence.mapper.InventoryMapper;
import com.zhao.inventory.infrastructure.persistence.po.InventoryDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 库存仓储实现
 */
@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {
    
    private final InventoryMapper inventoryMapper;
    private final InventoryPOConverter converter;
    
    @Override
    public Inventory save(Inventory inventory) {
        InventoryDO inventoryDO = converter.toPO(inventory);
        
        if (inventoryDO.getId() == null) {
            // 新增
            inventoryMapper.insert(inventoryDO);
            inventory.getId().setValue(inventoryDO.getId());
        } else {
            // 更新
            inventoryMapper.updateById(inventoryDO);
        }
        
        return inventory;
    }
    
    @Override
    public Optional<Inventory> findById(InventoryId id) {
        if (id == null || id.getValue() == null) {
            return Optional.empty();
        }
        
        InventoryDO inventoryDO = inventoryMapper.selectById(id.getValue());
        return inventoryDO != null ? Optional.of(converter.toDomain(inventoryDO)) : Optional.empty();
    }
    
    @Override
    public Optional<Inventory> findByProductId(ProductId productId) {
        if (productId == null || productId.getValue() == null) {
            return Optional.empty();
        }
        
        LambdaQueryWrapper<InventoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InventoryDO::getProductId, productId.getValue());
        
        InventoryDO inventoryDO = inventoryMapper.selectOne(wrapper);
        return inventoryDO != null ? Optional.of(converter.toDomain(inventoryDO)) : Optional.empty();
    }
    
    @Override
    public Optional<Inventory> findByProductIdAndWarehouseId(ProductId productId, WarehouseId warehouseId) {
        // 当前数据结构不支持仓库ID，直接按商品ID查询
        return findByProductId(productId);
    }
    
    @Override
    public List<Inventory> findByWarehouseId(WarehouseId warehouseId) {
        // 当前数据结构不支持仓库ID，返回所有库存
        List<InventoryDO> inventoryDOs = inventoryMapper.selectList(null);
        return inventoryDOs.stream()
                .map(converter::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Inventory> findLowStockInventories() {
        LambdaQueryWrapper<InventoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.lt(InventoryDO::getResidue, 10)
               .gt(InventoryDO::getResidue, 0);
        
        List<InventoryDO> inventoryDOs = inventoryMapper.selectList(wrapper);
        return inventoryDOs.stream()
                .map(converter::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Inventory> findOutOfStockInventories() {
        LambdaQueryWrapper<InventoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InventoryDO::getResidue, 0);
        
        List<InventoryDO> inventoryDOs = inventoryMapper.selectList(wrapper);
        return inventoryDOs.stream()
                .map(converter::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(InventoryId id) {
        if (id != null && id.getValue() != null) {
            inventoryMapper.deleteById(id.getValue());
        }
    }
    
    @Override
    public boolean existsById(InventoryId id) {
        if (id == null || id.getValue() == null) {
            return false;
        }
        return inventoryMapper.selectById(id.getValue()) != null;
    }
    
    @Override
    public boolean existsByProductId(ProductId productId) {
        if (productId == null || productId.getValue() == null) {
            return false;
        }
        
        LambdaQueryWrapper<InventoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InventoryDO::getProductId, productId.getValue());
        
        return inventoryMapper.selectCount(wrapper) > 0;
    }
}
