package com.zhao.order.infrastructure.remote.inventory;

import com.zhao.common.web.ApiResponse;
import com.zhao.common.web.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 库存服务降级工厂
 */
@Slf4j
@Component
public class InventoryClientFallbackFactory implements FallbackFactory<InventoryClient> {
    
    @Override
    public InventoryClient create(Throwable throwable) {
        return new InventoryClient() {
            @Override
            public ApiResponse<Void> deductInventory(Long productId, Integer quantity) {
                log.warn("库存服务调用失败，商品ID: {}, 数量: {}, 异常: {}", 
                        productId, quantity, throwable.getMessage());
                return ApiResponse.serviceDegraded("库存服务暂时不可用，请稍后重试");
            }
        };
    }
}


