package com.zhao.order.interfaces.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zhao.common.web.ApiResponse;
import com.zhao.order.application.command.CreateOrderCommand;
import com.zhao.order.application.service.OrderAppService;
import com.zhao.order.interfaces.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "订单管理", description = "订单相关接口")
public class OrderController {
    
    private final OrderAppService orderAppService;
    
    /**
     * 创建订单
     */
    @PostMapping
    @Operation(summary = "创建订单", description = "创建新订单")
    @SentinelResource(value = "createOrder")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody CreateOrderCommand command) {
        log.info("收到创建订单请求，用户ID: {}", command.getUserId());
        OrderResponse response = orderAppService.createOrder(command);
        return ApiResponse.ok(response);
    }
    
    /**
     * 根据ID查询订单
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "查询订单", description = "根据订单ID查询订单详情")
    @SentinelResource(value = "getOrderById")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        log.info("查询订单，订单ID: {}", orderId);
        OrderResponse response = orderAppService.getOrderById(orderId);
        return ApiResponse.ok(response);
    }
    
    /**
     * 根据订单号查询订单
     */
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "根据订单号查询订单", description = "根据订单号查询订单详情")
    @SentinelResource(value = "getOrderByOrderNumber")
    public ApiResponse<OrderResponse> getOrderByOrderNumber(@PathVariable String orderNumber) {
        log.info("根据订单号查询订单，订单号: {}", orderNumber);
        OrderResponse response = orderAppService.getOrderByOrderNumber(orderNumber);
        return ApiResponse.ok(response);
    }
    
    /**
     * 根据用户ID查询订单列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户订单列表", description = "根据用户ID查询订单列表")
    @SentinelResource(value = "getOrdersByUserId")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        log.info("查询用户订单列表，用户ID: {}", userId);
        List<OrderResponse> response = orderAppService.getOrdersByUserId(userId);
        return ApiResponse.ok(response);
    }
    
    /**
     * 取消订单
     */
    @PostMapping("/{orderId}/cancel")
    @Operation(summary = "取消订单", description = "取消指定订单")
    @SentinelResource(value = "cancelOrder")
    public ApiResponse<Void> cancelOrder(@PathVariable Long orderId) {
        log.info("取消订单，订单ID: {}", orderId);
        orderAppService.cancelOrder(orderId);
        return ApiResponse.ok();
    }
    
    /**
     * 支付订单
     */
    @PostMapping("/{orderId}/pay")
    @Operation(summary = "支付订单", description = "支付指定订单")
    @SentinelResource(value = "payOrder")
    public ApiResponse<Void> payOrder(@PathVariable Long orderId) {
        log.info("支付订单，订单ID: {}", orderId);
        orderAppService.payOrder(orderId);
        return ApiResponse.ok();
    }
    
    /**
     * 发货
     */
    @PostMapping("/{orderId}/ship")
    @Operation(summary = "订单发货", description = "订单发货")
    @SentinelResource(value = "shipOrder")
    public ApiResponse<Void> shipOrder(@PathVariable Long orderId) {
        log.info("订单发货，订单ID: {}", orderId);
        orderAppService.shipOrder(orderId);
        return ApiResponse.ok();
    }
    
    /**
     * 完成订单
     */
    @PostMapping("/{orderId}/complete")
    @Operation(summary = "完成订单", description = "完成指定订单")
    @SentinelResource(value = "completeOrder")
    public ApiResponse<Void> completeOrder(@PathVariable Long orderId) {
        log.info("完成订单，订单ID: {}", orderId);
        orderAppService.completeOrder(orderId);
        return ApiResponse.ok();
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/ping")
    @SentinelResource(value = "ping")
    @Operation(summary = "健康检查", description = "订单服务健康检查")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("order-service is running1");
    }
    
    /**
     * Sentinel流控处理方法
     */
    

}