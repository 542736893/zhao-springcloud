package com.zhao.inventory.application.assembler;

import com.zhao.inventory.application.command.DeductInventoryCommand;
import com.zhao.inventory.application.command.RestoreInventoryCommand;
import com.zhao.inventory.application.query.InventoryQuery;
import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.interfaces.dto.request.DeductInventoryRequest;
import com.zhao.inventory.interfaces.dto.request.QueryInventoryRequest;
import com.zhao.inventory.interfaces.dto.response.InventoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * 库存装配器
 */
@Mapper(componentModel = "spring")
public interface InventoryAssembler {
    
    // ========== Request to Command ==========
    
    /**
     * 扣减库存请求转命令
     */
    DeductInventoryCommand toCommand(DeductInventoryRequest request);
    
    /**
     * 查询请求转查询对象
     */
    InventoryQuery toQuery(QueryInventoryRequest request);
    
    // ========== Domain to Response ==========
    
    /**
     * 领域对象转响应
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "productId", source = "productId.value")
    @Mapping(target = "warehouseId", source = "warehouseId.value")
    @Mapping(target = "availableQuantity", source = "availableQuantity.value")
    @Mapping(target = "reservedQuantity", source = "reservedQuantity.value")
    @Mapping(target = "totalQuantity", source = "totalQuantity.value")
    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "unitCode", source = "unit.code")
    @Mapping(target = "unitName", source = "unit.name")
    @Mapping(target = "locationCode", source = "location.locationCode")
    InventoryResponse toResponse(Inventory inventory);
    
    /**
     * 领域对象列表转响应列表
     */
    List<InventoryResponse> toResponseList(List<Inventory> inventories);
    
    // ========== Command Factory Methods ==========
    
    /**
     * 创建恢复库存命令
     */
    default RestoreInventoryCommand createRestoreCommand(Long productId, Integer quantity, String reason) {
        return new RestoreInventoryCommand(productId, quantity, reason);
    }
}
