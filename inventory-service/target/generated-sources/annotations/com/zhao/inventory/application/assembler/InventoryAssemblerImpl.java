package com.zhao.inventory.application.assembler;

import com.zhao.inventory.application.command.DeductInventoryCommand;
import com.zhao.inventory.application.query.InventoryQuery;
import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.model.valueobject.InventoryId;
import com.zhao.inventory.domain.model.valueobject.InventoryStatus;
import com.zhao.inventory.domain.model.valueobject.Location;
import com.zhao.inventory.domain.model.valueobject.ProductId;
import com.zhao.inventory.domain.model.valueobject.Quantity;
import com.zhao.inventory.domain.model.valueobject.Unit;
import com.zhao.inventory.domain.model.valueobject.WarehouseId;
import com.zhao.inventory.interfaces.dto.request.DeductInventoryRequest;
import com.zhao.inventory.interfaces.dto.request.QueryInventoryRequest;
import com.zhao.inventory.interfaces.dto.response.InventoryResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-20T21:09:36+0800",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class InventoryAssemblerImpl implements InventoryAssembler {

    @Override
    public DeductInventoryCommand toCommand(DeductInventoryRequest request) {
        if ( request == null ) {
            return null;
        }

        DeductInventoryCommand deductInventoryCommand = new DeductInventoryCommand();

        deductInventoryCommand.setProductId( request.getProductId() );
        deductInventoryCommand.setQuantity( request.getQuantity() );
        deductInventoryCommand.setWarehouseId( request.getWarehouseId() );
        deductInventoryCommand.setReason( request.getReason() );

        return deductInventoryCommand;
    }

    @Override
    public InventoryQuery toQuery(QueryInventoryRequest request) {
        if ( request == null ) {
            return null;
        }

        InventoryQuery inventoryQuery = new InventoryQuery();

        inventoryQuery.setProductId( request.getProductId() );
        inventoryQuery.setWarehouseId( request.getWarehouseId() );
        inventoryQuery.setStatus( request.getStatus() );
        inventoryQuery.setMinQuantity( request.getMinQuantity() );
        inventoryQuery.setMaxQuantity( request.getMaxQuantity() );
        inventoryQuery.setLowStockOnly( request.getLowStockOnly() );
        inventoryQuery.setOutOfStockOnly( request.getOutOfStockOnly() );

        return inventoryQuery;
    }

    @Override
    public InventoryResponse toResponse(Inventory inventory) {
        if ( inventory == null ) {
            return null;
        }

        InventoryResponse inventoryResponse = new InventoryResponse();

        inventoryResponse.setId( inventoryIdValue( inventory ) );
        inventoryResponse.setProductId( inventoryProductIdValue( inventory ) );
        inventoryResponse.setWarehouseId( inventoryWarehouseIdValue( inventory ) );
        inventoryResponse.setAvailableQuantity( inventoryAvailableQuantityValue( inventory ) );
        inventoryResponse.setReservedQuantity( inventoryReservedQuantityValue( inventory ) );
        inventoryResponse.setTotalQuantity( inventoryTotalQuantityValue( inventory ) );
        inventoryResponse.setStatus( inventoryStatusCode( inventory ) );
        inventoryResponse.setUnitCode( inventoryUnitCode( inventory ) );
        inventoryResponse.setUnitName( inventoryUnitName( inventory ) );
        inventoryResponse.setLocationCode( inventoryLocationLocationCode( inventory ) );
        inventoryResponse.setCreateTime( inventory.getCreateTime() );
        inventoryResponse.setUpdateTime( inventory.getUpdateTime() );

        return inventoryResponse;
    }

    @Override
    public List<InventoryResponse> toResponseList(List<Inventory> inventories) {
        if ( inventories == null ) {
            return null;
        }

        List<InventoryResponse> list = new ArrayList<InventoryResponse>( inventories.size() );
        for ( Inventory inventory : inventories ) {
            list.add( toResponse( inventory ) );
        }

        return list;
    }

    private Long inventoryIdValue(Inventory inventory) {
        InventoryId id = inventory.getId();
        if ( id == null ) {
            return null;
        }
        return id.getValue();
    }

    private Long inventoryProductIdValue(Inventory inventory) {
        ProductId productId = inventory.getProductId();
        if ( productId == null ) {
            return null;
        }
        return productId.getValue();
    }

    private Long inventoryWarehouseIdValue(Inventory inventory) {
        WarehouseId warehouseId = inventory.getWarehouseId();
        if ( warehouseId == null ) {
            return null;
        }
        return warehouseId.getValue();
    }

    private BigDecimal inventoryAvailableQuantityValue(Inventory inventory) {
        Quantity availableQuantity = inventory.getAvailableQuantity();
        if ( availableQuantity == null ) {
            return null;
        }
        return availableQuantity.getValue();
    }

    private BigDecimal inventoryReservedQuantityValue(Inventory inventory) {
        Quantity reservedQuantity = inventory.getReservedQuantity();
        if ( reservedQuantity == null ) {
            return null;
        }
        return reservedQuantity.getValue();
    }

    private BigDecimal inventoryTotalQuantityValue(Inventory inventory) {
        Quantity totalQuantity = inventory.getTotalQuantity();
        if ( totalQuantity == null ) {
            return null;
        }
        return totalQuantity.getValue();
    }

    private String inventoryStatusCode(Inventory inventory) {
        InventoryStatus status = inventory.getStatus();
        if ( status == null ) {
            return null;
        }
        return status.getCode();
    }

    private String inventoryUnitCode(Inventory inventory) {
        Unit unit = inventory.getUnit();
        if ( unit == null ) {
            return null;
        }
        return unit.getCode();
    }

    private String inventoryUnitName(Inventory inventory) {
        Unit unit = inventory.getUnit();
        if ( unit == null ) {
            return null;
        }
        return unit.getName();
    }

    private String inventoryLocationLocationCode(Inventory inventory) {
        Location location = inventory.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getLocationCode();
    }
}
