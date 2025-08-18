package com.zhao.inventory.application.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.common.exception.BusinessException;
import com.zhao.common.web.ErrorCode;
import com.zhao.inventory.infrastructure.persistence.mapper.InventoryMapper;
import com.zhao.inventory.infrastructure.persistence.po.InventoryDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryAppService {

    private final InventoryMapper inventoryMapper;

    public InventoryAppService(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    public InventoryDO findByProduct(Long productId) {
        return inventoryMapper.selectOne(new LambdaQueryWrapper<InventoryDO>()
                .eq(InventoryDO::getProductId, productId));
    }

    @Transactional
    public void deduct(Long productId, Integer count) {
        InventoryDO inv = findByProduct(productId);
        if (inv == null || inv.getResidue() < count) {
            throw new BusinessException(ErrorCode.SERVER_ERROR.getCode(), "库存不足");
        }
        inv.setUsed(inv.getUsed() + count);
        inv.setResidue(inv.getResidue() - count);
        inventoryMapper.updateById(inv);
    }
}


