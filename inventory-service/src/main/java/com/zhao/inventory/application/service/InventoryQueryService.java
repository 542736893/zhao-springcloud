package com.zhao.inventory.application.service;

import com.zhao.inventory.application.assembler.InventoryAssembler;
import com.zhao.inventory.application.query.InventoryQuery;
import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.repository.InventoryRepository;
import com.zhao.inventory.interfaces.dto.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存查询服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryQueryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryAssembler inventoryAssembler;

    /**
     * 查询库存统计信息
     */
    public InventoryStatistics getInventoryStatistics() {
        List<Inventory> lowStockInventories = inventoryRepository.findLowStockInventories();
        List<Inventory> outOfStockInventories = inventoryRepository.findOutOfStockInventories();
        
        return InventoryStatistics.builder()
                .lowStockCount(lowStockInventories.size())
                .outOfStockCount(outOfStockInventories.size())
                .build();
    }

    /**
     * 查询库存预警信息
     */
    public List<InventoryResponse> getInventoryAlerts() {
        List<Inventory> lowStockInventories = inventoryRepository.findLowStockInventories();
        List<Inventory> outOfStockInventories = inventoryRepository.findOutOfStockInventories();
        
        List<InventoryResponse> alerts = inventoryAssembler.toResponseList(lowStockInventories);
        alerts.addAll(inventoryAssembler.toResponseList(outOfStockInventories));
        
        return alerts;
    }

    /**
     * 库存统计信息
     */
    public static class InventoryStatistics {
        private int lowStockCount;
        private int outOfStockCount;
        
        public static InventoryStatisticsBuilder builder() {
            return new InventoryStatisticsBuilder();
        }
        
        public int getLowStockCount() {
            return lowStockCount;
        }
        
        public int getOutOfStockCount() {
            return outOfStockCount;
        }
        
        public static class InventoryStatisticsBuilder {
            private int lowStockCount;
            private int outOfStockCount;
            
            public InventoryStatisticsBuilder lowStockCount(int lowStockCount) {
                this.lowStockCount = lowStockCount;
                return this;
            }
            
            public InventoryStatisticsBuilder outOfStockCount(int outOfStockCount) {
                this.outOfStockCount = outOfStockCount;
                return this;
            }
            
            public InventoryStatistics build() {
                InventoryStatistics statistics = new InventoryStatistics();
                statistics.lowStockCount = this.lowStockCount;
                statistics.outOfStockCount = this.outOfStockCount;
                return statistics;
            }
        }
    }
}
