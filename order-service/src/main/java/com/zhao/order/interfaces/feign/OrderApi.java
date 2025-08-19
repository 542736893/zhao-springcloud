package com.zhao.order.interfaces.feign;

import com.zhao.common.web.ApiResponse;
import com.zhao.order.interfaces.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order-service", contextId = "OrderApi", path = "/api/order")
public interface OrderApi {
    
    @GetMapping("/{orderId}")
    ApiResponse<OrderResponse> getOrder(@PathVariable("orderId") Long orderId);
    
    @GetMapping("/ping")
    ApiResponse<String> ping();
}
