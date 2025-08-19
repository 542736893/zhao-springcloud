package com.zhao.inventory.infrastructure.persistence.converter;

import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.infrastructure.persistence.po.InventoryDO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-19T18:59:12+0800",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
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
