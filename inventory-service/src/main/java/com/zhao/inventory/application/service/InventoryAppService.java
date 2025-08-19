package com.zhao.inventory.application.service;

import com.zhao.common.exception.BusinessException;
import com.zhao.common.web.ErrorCode;
import com.zhao.inventory.application.assembler.InventoryAssembler;
import com.zhao.inventory.application.command.DeductInventoryCommand;
import com.zhao.inventory.application.command.RestoreInventoryCommand;
import com.zhao.inventory.application.query.InventoryQuery;
import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.model.valueobject.ProductId;
import com.zhao.inventory.domain.model.valueobject.Quantity;
import com.zhao.inventory.domain.repository.InventoryRepository;
import com.zhao.inventory.interfaces.dto.response.InventoryResponse;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 库存应用服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryAppService {

    private final InventoryRepository inventoryRepository;
    private final InventoryAssembler inventoryAssembler;

    /**
     * 扣减库存
     */
    @Transactional
    public void deductInventory(DeductInventoryCommand command) {
        log.info("开始扣减库存，商品ID: {}, 数量: {}", command.getProductId(), command.getQuantity());

        // 查找库存
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(ProductId.of(command.getProductId()));
        if (!inventoryOpt.isPresent()) {
            log.warn("库存记录不存在，商品ID: {}", command.getProductId());
            throw new BusinessException(ErrorCode.INVENTORY_NOT_FOUND.getCode(), "库存记录不存在");
        }

        Inventory inventory = inventoryOpt.get();

        // 扣减库存（业务逻辑在领域对象中）
        try {
            inventory.deduct(Quantity.of(command.getQuantity()));
            inventoryRepository.save(inventory);
            log.info("库存扣减成功，商品ID: {}, 扣减数量: {}", command.getProductId(), command.getQuantity());
        } catch (IllegalStateException e) {
            log.warn("库存扣减失败: {}", e.getMessage());
            throw new BusinessException(ErrorCode.INVENTORY_INSUFFICIENT.getCode(), "库存不足");
        }
    }

    /**
     * 恢复库存
     */
    @Transactional
    public void restoreInventory(RestoreInventoryCommand command) {
        log.info("开始恢复库存，商品ID: {}, 数量: {}", command.getProductId(), command.getQuantity());

        // 查找库存
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(ProductId.of(command.getProductId()));
        if (!inventoryOpt.isPresent()) {
            log.warn("库存记录不存在，商品ID: {}", command.getProductId());
            throw new BusinessException(ErrorCode.INVENTORY_NOT_FOUND.getCode(), "库存记录不存在");
        }

        Inventory inventory = inventoryOpt.get();

        // 恢复库存
        inventory.restore(Quantity.of(command.getQuantity()));
        inventoryRepository.save(inventory);
        log.info("库存恢复成功，商品ID: {}, 恢复数量: {}", command.getProductId(), command.getQuantity());
    }

    /**
     * 查询库存
     */
    public InventoryResponse queryInventory(InventoryQuery query) {
        if (query.getProductId() == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "商品ID不能为空");
        }

        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(ProductId.of(query.getProductId()));
        if (!inventoryOpt.isPresent()) {
            throw new BusinessException(ErrorCode.INVENTORY_NOT_FOUND.getCode(), "库存记录不存在");
        }

        return inventoryAssembler.toResponse(inventoryOpt.get());
    }

    /**
     * 查询低库存商品
     */
    public List<InventoryResponse> queryLowStockInventories() {
        List<Inventory> inventories = inventoryRepository.findLowStockInventories();
        return inventoryAssembler.toResponseList(inventories);
    }

    /**
     * 查询缺货商品
     */
    public List<InventoryResponse> queryOutOfStockInventories() {
        List<Inventory> inventories = inventoryRepository.findOutOfStockInventories();
        return inventoryAssembler.toResponseList(inventories);
    }

    // ========== 兼容性方法（保持与现有代码的兼容性）==========

    /**
     * 扣减库存（兼容性方法）
     */
    @Transactional
    public void deduct(Long productId, Integer quantity) {
        DeductInventoryCommand command = new DeductInventoryCommand(productId, quantity);
        deductInventory(command);
    }

    /**
     * 根据商品ID查找库存（兼容性方法）
     */
    public InventoryResponse findByProduct(Long productId) {
        InventoryQuery query = new InventoryQuery(productId);
        return queryInventory(query);
    }
}