package com.zhao.inventory.interfaces.web;

import com.zhao.common.web.ApiResponse;
import com.zhao.inventory.application.service.InventoryAppService;
import com.zhao.inventory.infrastructure.persistence.po.InventoryDO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryAppService inventoryAppService;

    public InventoryController(InventoryAppService inventoryAppService) {
        this.inventoryAppService = inventoryAppService;
    }

    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("pong");
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<InventoryDO> get(@PathVariable Long productId) {
        return ApiResponse.ok(inventoryAppService.findByProduct(productId));
    }

    @PostMapping("/deduct")
    public ApiResponse<Void> deduct(@RequestParam Long productId, @RequestParam Integer count) {
        inventoryAppService.deduct(productId, count);
        return ApiResponse.ok(null);
    }
}


