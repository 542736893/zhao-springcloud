package com.zhao.order.infrastructure.remote.inventory;

import com.zhao.common.web.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存服务Feign客户端
 */
@FeignClient(
    name = "inventory-service",
    contextId = "InventoryClient",
    path = "/api/v1/inventory",
    fallbackFactory = InventoryClientFallbackFactory.class
)
public interface InventoryClient {
    
    /**
     * 扣减库存
     */
    @PostMapping("/deduct")
    ApiResponse<Void> deductInventory(@RequestParam("productId") Long productId, 
                                    @RequestParam("quantity") Integer quantity);
}


