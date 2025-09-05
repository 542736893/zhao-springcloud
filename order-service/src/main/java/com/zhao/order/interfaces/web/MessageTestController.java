package com.zhao.order.interfaces.web;

import com.zhao.common.web.ApiResponse;
import com.zhao.order.application.service.OrderMessageService;
import com.zhao.order.interfaces.dto.message.CustomMessageRequest;
import com.zhao.order.interfaces.dto.message.SendMessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 消息测试控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order/message")
@RequiredArgsConstructor
@Validated
@Tag(name = "消息测试接口", description = "RocketMQ消息发送和接收测试接口")
public class MessageTestController {
    
    private final OrderMessageService orderMessageService;
    
    @PostMapping("/send-order-created")
    @Operation(summary = "发送订单创建消息", description = "发送订单创建消息到RocketMQ")
    public ApiResponse<String> sendOrderCreatedMessage(@Valid @RequestBody SendMessageRequest request) {
        try {
            log.info("接收到发送订单创建消息请求: orderId={}, orderNumber={}", 
                    request.getOrderId(), request.getOrderNumber());
            
            orderMessageService.sendOrderCreatedMessage(request);
            
            return ApiResponse.ok("订单创建消息发送成功");
        } catch (Exception e) {
            log.error("发送订单创建消息失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("发送订单创建消息失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/send-order-updated")
    @Operation(summary = "发送订单状态更新消息", description = "发送订单状态更新消息到RocketMQ")
    public ApiResponse<String> sendOrderUpdatedMessage(@Valid @RequestBody SendMessageRequest request) {
        try {
            log.info("接收到发送订单状态更新消息请求: orderId={}, orderNumber={}, status={}", 
                    request.getOrderId(), request.getOrderNumber(), request.getOrderStatus());
            
            orderMessageService.sendOrderUpdatedMessage(request);
            
            return ApiResponse.ok("订单状态更新消息发送成功");
        } catch (Exception e) {
            log.error("发送订单状态更新消息失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("发送订单状态更新消息失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/send-order-paid")
    @Operation(summary = "发送订单支付消息", description = "发送订单支付消息到RocketMQ")
    public ApiResponse<String> sendOrderPaidMessage(@Valid @RequestBody SendMessageRequest request) {
        try {
            log.info("接收到发送订单支付消息请求: orderId={}, orderNumber={}, amount={}", 
                    request.getOrderId(), request.getOrderNumber(), request.getOrderAmount());
            
            orderMessageService.sendOrderPaidMessage(request);
            
            return ApiResponse.ok("订单支付消息发送成功");
        } catch (Exception e) {
            log.error("发送订单支付消息失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("发送订单支付消息失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/send-custom")
    @Operation(summary = "发送自定义消息", description = "发送自定义消息到RocketMQ")
    public ApiResponse<String> sendCustomMessage(@Valid @RequestBody CustomMessageRequest request) {
        try {
            log.info("接收到发送自定义消息请求: topic={}, tag={}, key={}", 
                    request.getTopic(), request.getTag(), request.getKey());
            
            orderMessageService.sendCustomMessage(request);
            
            return ApiResponse.ok("自定义消息发送成功");
        } catch (Exception e) {
            log.error("发送自定义消息失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("发送自定义消息失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/simulate-order-created")
    @Operation(summary = "模拟订单创建", description = "模拟订单创建并发送消息")
    public ApiResponse<String> simulateOrderCreated(
            @Parameter(description = "订单ID") @RequestParam @NotNull Long orderId,
            @Parameter(description = "订单号") @RequestParam @NotBlank String orderNumber,
            @Parameter(description = "用户ID") @RequestParam @NotNull Long userId) {
        try {
            log.info("接收到模拟订单创建请求: orderId={}, orderNumber={}, userId={}", 
                    orderId, orderNumber, userId);
            
            orderMessageService.simulateOrderCreated(orderId, orderNumber, userId);
            
            return ApiResponse.ok("模拟订单创建消息发送成功");
        } catch (Exception e) {
            log.error("模拟订单创建失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("模拟订单创建失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/simulate-order-updated")
    @Operation(summary = "模拟订单状态更新", description = "模拟订单状态更新并发送消息")
    public ApiResponse<String> simulateOrderUpdated(
            @Parameter(description = "订单ID") @RequestParam @NotNull Long orderId,
            @Parameter(description = "订单号") @RequestParam @NotBlank String orderNumber,
            @Parameter(description = "用户ID") @RequestParam @NotNull Long userId,
            @Parameter(description = "订单状态") @RequestParam @NotBlank String status) {
        try {
            log.info("接收到模拟订单状态更新请求: orderId={}, orderNumber={}, userId={}, status={}", 
                    orderId, orderNumber, userId, status);
            
            orderMessageService.simulateOrderUpdated(orderId, orderNumber, userId, status);
            
            return ApiResponse.ok("模拟订单状态更新消息发送成功");
        } catch (Exception e) {
            log.error("模拟订单状态更新失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("模拟订单状态更新失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/test-simple")
    @Operation(summary = "简单测试", description = "发送一个简单的测试消息")
    public ApiResponse<String> testSimple() {
        try {
            log.info("接收到简单测试请求");
            
            // 发送一个简单的测试消息
            orderMessageService.simulateOrderCreated(12345L, "TEST-ORDER-001", 1001L);
            
            return ApiResponse.ok("简单测试消息发送成功，请查看日志确认消息是否被消费");
        } catch (Exception e) {
            log.error("简单测试失败: {}", e.getMessage(), e);
            return ApiResponse.systemError("简单测试失败: " + e.getMessage());
        }
    }
}
