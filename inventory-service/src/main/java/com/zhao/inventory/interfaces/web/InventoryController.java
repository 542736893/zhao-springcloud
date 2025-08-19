package com.zhao.inventory.interfaces.web;

import com.zhao.common.web.ApiResponse;
import com.zhao.inventory.application.assembler.InventoryAssembler;
import com.zhao.inventory.application.command.DeductInventoryCommand;
import com.zhao.inventory.application.command.RestoreInventoryCommand;
import com.zhao.inventory.application.query.InventoryQuery;
import com.zhao.inventory.application.service.InventoryAppService;
import com.zhao.inventory.domain.model.aggregate.Inventory;
import com.zhao.inventory.domain.model.valueobject.ProductId;
import com.zhao.inventory.domain.model.valueobject.Quantity;
import com.zhao.inventory.domain.repository.InventoryRepository;
import com.zhao.inventory.interfaces.dto.request.DeductInventoryRequest;
import com.zhao.inventory.interfaces.dto.request.QueryInventoryRequest;
import com.zhao.inventory.interfaces.dto.response.InventoryResponse;
import com.zhao.inventory.interfaces.feign.InventoryApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 库存控制器
 */
@Tag(name = "库存管理", description = "库存管理相关接口")
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Validated
public class InventoryController implements InventoryApi {

    private final InventoryAppService inventoryAppService;
    private final InventoryAssembler inventoryAssembler;
    private final InventoryRepository inventoryRepository;

    @Operation(summary = "健康检查", description = "检查服务是否正常运行")
    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("pong");
    }

    @Override
    @Operation(summary = "扣减库存", description = "扣减指定商品的库存数量")
    @PostMapping("/deduct")
    public ApiResponse<Void> deductInventory(
            @Parameter(description = "商品ID", required = true)
            @RequestParam("productId") Long productId,
            @Parameter(description = "扣减数量", required = true)
            @RequestParam("quantity") Integer quantity) {

        DeductInventoryCommand command = new DeductInventoryCommand(productId, quantity);
        inventoryAppService.deductInventory(command);
        return ApiResponse.ok(null);
    }

    @Override
    @Operation(summary = "恢复库存", description = "恢复指定商品的库存数量")
    @PostMapping("/restore")
    public ApiResponse<Void> restoreInventory(
            @Parameter(description = "商品ID", required = true)
            @RequestParam("productId") Long productId,
            @Parameter(description = "恢复数量", required = true)
            @RequestParam("quantity") Integer quantity) {

        RestoreInventoryCommand command = new RestoreInventoryCommand(productId, quantity);
        inventoryAppService.restoreInventory(command);
        return ApiResponse.ok(null);
    }

    @Override
    @Operation(summary = "查询库存", description = "查询指定商品的库存信息")
    @GetMapping("/product/{productId}")
    public ApiResponse<InventoryResponse> getInventory(
            @Parameter(description = "商品ID", required = true)
            @PathVariable("productId") Long productId) {

        InventoryQuery query = new InventoryQuery(productId);
        InventoryResponse response = inventoryAppService.queryInventory(query);
        return ApiResponse.ok(response);
    }

    @Override
    @Operation(summary = "检查库存", description = "检查指定商品的库存是否充足")
    @GetMapping("/check")
    public ApiResponse<Boolean> checkInventory(
            @Parameter(description = "商品ID", required = true)
            @RequestParam("productId") Long productId,
            @Parameter(description = "需要数量", required = true)
            @RequestParam("quantity") Integer quantity) {

        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(ProductId.of(productId));
        if (!inventoryOpt.isPresent()) {
            return ApiResponse.ok(false);
        }

        boolean hasEnough = inventoryOpt.get().hasEnoughStock(Quantity.of(quantity));
        return ApiResponse.ok(hasEnough);
    }

    @Operation(summary = "扣减库存（请求体）", description = "通过请求体扣减库存")
    @PostMapping("/deduct-body")
    public ApiResponse<Void> deductInventoryByBody(@Valid @RequestBody DeductInventoryRequest request) {
        DeductInventoryCommand command = inventoryAssembler.toCommand(request);
        inventoryAppService.deductInventory(command);
        return ApiResponse.ok(null);
    }

    @Operation(summary = "查询库存列表", description = "根据条件查询库存列表")
    @PostMapping("/query")
    public ApiResponse<List<InventoryResponse>> queryInventories(@RequestBody QueryInventoryRequest request) {
        InventoryQuery query = inventoryAssembler.toQuery(request);

        if (query.getLowStockOnly() != null && query.getLowStockOnly()) {
            List<InventoryResponse> responses = inventoryAppService.queryLowStockInventories();
            return ApiResponse.ok(responses);
        }

        if (query.getOutOfStockOnly() != null && query.getOutOfStockOnly()) {
            List<InventoryResponse> responses = inventoryAppService.queryOutOfStockInventories();
            return ApiResponse.ok(responses);
        }

        // 单个商品查询
        if (query.getProductId() != null) {
            InventoryResponse response = inventoryAppService.queryInventory(query);
            return ApiResponse.ok(List.of(response));
        }

        return ApiResponse.ok(List.of());
    }

    @Operation(summary = "查询低库存商品", description = "查询所有低库存商品")
    @GetMapping("/low-stock")
    public ApiResponse<List<InventoryResponse>> getLowStockInventories() {
        List<InventoryResponse> responses = inventoryAppService.queryLowStockInventories();
        return ApiResponse.ok(responses);
    }

    @Operation(summary = "查询缺货商品", description = "查询所有缺货商品")
    @GetMapping("/out-of-stock")
    public ApiResponse<List<InventoryResponse>> getOutOfStockInventories() {
        List<InventoryResponse> responses = inventoryAppService.queryOutOfStockInventories();
        return ApiResponse.ok(responses);
    }
}


