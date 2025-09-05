package com.zhao.order.application.service;

import com.zhao.order.domain.constants.MessageConstants;
import com.zhao.order.infrastructure.messaging.OrderMessageProducer;
import com.zhao.order.interfaces.dto.message.CustomMessageRequest;
import com.zhao.order.interfaces.dto.message.OrderMessage;
import com.zhao.order.interfaces.dto.message.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 订单消息服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMessageService {
    
    private final OrderMessageProducer orderMessageProducer;
    
    /**
     * 发送订单创建消息
     */
    public void sendOrderCreatedMessage(SendMessageRequest request) {
        log.info("准备发送订单创建消息: orderId={}, orderNumber={}", 
                request.getOrderId(), request.getOrderNumber());
        
        OrderMessage orderMessage = OrderMessage.builder()
                .orderId(request.getOrderId())
                .orderNumber(request.getOrderNumber())
                .userId(request.getUserId())
                .messageType(MessageConstants.MESSAGE_TYPE_ORDER_CREATED)
                .content(request.getContent())
                .orderStatus(request.getOrderStatus())
                .orderAmount(request.getOrderAmount())
                .timestamp(LocalDateTime.now())
                .extInfo(request.getExtInfo())
                .build();
        
        orderMessageProducer.sendOrderCreatedMessage(orderMessage);
        
        log.info("订单创建消息发送完成: orderId={}", request.getOrderId());
    }
    
    /**
     * 发送订单状态更新消息
     */
    public void sendOrderUpdatedMessage(SendMessageRequest request) {
        log.info("准备发送订单状态更新消息: orderId={}, orderNumber={}, status={}", 
                request.getOrderId(), request.getOrderNumber(), request.getOrderStatus());
        
        OrderMessage orderMessage = OrderMessage.builder()
                .orderId(request.getOrderId())
                .orderNumber(request.getOrderNumber())
                .userId(request.getUserId())
                .messageType(MessageConstants.MESSAGE_TYPE_ORDER_UPDATED)
                .content(request.getContent())
                .orderStatus(request.getOrderStatus())
                .orderAmount(request.getOrderAmount())
                .timestamp(LocalDateTime.now())
                .extInfo(request.getExtInfo())
                .build();
        
        orderMessageProducer.sendOrderUpdatedMessage(orderMessage);
        
        log.info("订单状态更新消息发送完成: orderId={}", request.getOrderId());
    }
    
    /**
     * 发送订单支付消息
     */
    public void sendOrderPaidMessage(SendMessageRequest request) {
        log.info("准备发送订单支付消息: orderId={}, orderNumber={}, amount={}", 
                request.getOrderId(), request.getOrderNumber(), request.getOrderAmount());
        
        OrderMessage orderMessage = OrderMessage.builder()
                .orderId(request.getOrderId())
                .orderNumber(request.getOrderNumber())
                .userId(request.getUserId())
                .messageType(MessageConstants.MESSAGE_TYPE_ORDER_PAID)
                .content(request.getContent())
                .orderStatus(request.getOrderStatus())
                .orderAmount(request.getOrderAmount())
                .timestamp(LocalDateTime.now())
                .extInfo(request.getExtInfo())
                .build();
        
        orderMessageProducer.sendOrderPaidMessage(orderMessage);
        
        log.info("订单支付消息发送完成: orderId={}", request.getOrderId());
    }
    
    /**
     * 发送自定义消息
     */
    public void sendCustomMessage(CustomMessageRequest request) {
        log.info("准备发送自定义消息: topic={}, tag={}, key={}", 
                request.getTopic(), request.getTag(), request.getKey());
        
        orderMessageProducer.sendCustomMessage(
                request.getTopic(), 
                request.getTag(), 
                request.getContent(), 
                request.getKey()
        );
        
        log.info("自定义消息发送完成: topic={}", request.getTopic());
    }
    
    /**
     * 模拟订单创建并发送消息
     */
    public void simulateOrderCreated(Long orderId, String orderNumber, Long userId) {
        SendMessageRequest request = new SendMessageRequest();
        request.setOrderId(orderId);
        request.setOrderNumber(orderNumber);
        request.setUserId(userId);
        request.setContent("订单创建成功，等待支付");
        request.setOrderStatus("CREATED");
        request.setOrderAmount(99.99);
        request.setExtInfo("{\"source\":\"simulation\"}");
        
        sendOrderCreatedMessage(request);
    }
    
    /**
     * 模拟订单状态更新并发送消息
     */
    public void simulateOrderUpdated(Long orderId, String orderNumber, Long userId, String status) {
        SendMessageRequest request = new SendMessageRequest();
        request.setOrderId(orderId);
        request.setOrderNumber(orderNumber);
        request.setUserId(userId);
        request.setContent("订单状态已更新为: " + status);
        request.setOrderStatus(status);
        request.setOrderAmount(99.99);
        request.setExtInfo("{\"source\":\"simulation\"}");
        
        sendOrderUpdatedMessage(request);
    }
}
