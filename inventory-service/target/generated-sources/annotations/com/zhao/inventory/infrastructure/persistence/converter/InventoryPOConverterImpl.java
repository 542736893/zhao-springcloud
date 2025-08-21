package com.zhao.inventory.infrastructure.persistence.converter;

import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.infrastructure.persistence.po.InventoryDO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-20T21:09:36+0800",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class InventoryPOConverterImpl implements InventoryPOConverter {

    @Override
    public InventoryDO toPO(Inventory inventory) {
        if ( inventory == null ) {
            return null;
        }

        InventoryDO inventoryDO = new InventoryDO();

        inventoryDO.setId( inventoryIdToLong( inventory.getId() ) );
        inventoryDO.setProductId( productIdToLong( inventory.getProductId() ) );
        inventoryDO.setTotal( quantityToInteger( inventory.getTotalQuantity() ) );
        inventoryDO.setResidue( quantityToInteger( inventory.getAvailableQuantity() ) );
        inventoryDO.setCreateTime( inventory.getCreateTime() );
        inventoryDO.setUpdateTime( inventory.getUpdateTime() );

        inventoryDO.setUsed( calculateUsed(inventory) );

        return inventoryDO;
    }
}
