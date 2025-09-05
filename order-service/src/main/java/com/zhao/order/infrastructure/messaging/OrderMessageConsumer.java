package com.zhao.order.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.order.domain.constants.MessageConstants;
import com.zhao.order.interfaces.dto.message.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 订单消息消费者
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.cloud.rocketmq.name-server")
@RocketMQMessageListener(
        topic = MessageConstants.ORDER_TOPIC,
        consumerGroup = MessageConstants.ORDER_CONSUMER_GROUP,
        selectorExpression = "*"
)
public class OrderMessageConsumer implements RocketMQListener<String> {
    
    private final ObjectMapper objectMapper;
    
    @Override
    public void onMessage(String message) {
        try {
            log.info("接收到订单消息: {}", message);
            
            // 尝试解析为OrderMessage
            try {
                OrderMessage orderMessage = objectMapper.readValue(message, OrderMessage.class);
                processOrderMessage(orderMessage);
            } catch (Exception e) {
                // 如果不是OrderMessage格式，则作为普通文本消息处理
                log.info("接收到普通文本消息: {}", message);
                processTextMessage(message);
            }
            
        } catch (Exception e) {
            log.error("处理订单消息失败: {}", e.getMessage(), e);
            // 这里可以根据业务需要决定是否抛出异常来触发重试
            // throw new RuntimeException("处理订单消息失败", e);
        }
    }
    
    /**
     * 处理订单消息
     */
    private void processOrderMessage(OrderMessage orderMessage) {
        log.info("处理订单消息开始: messageType={}, orderId={}, orderNumber={}", 
                orderMessage.getMessageType(), orderMessage.getOrderId(), orderMessage.getOrderNumber());
        
        switch (orderMessage.getMessageType()) {
            case MessageConstants.MESSAGE_TYPE_ORDER_CREATED:
                handleOrderCreated(orderMessage);
                break;
            case MessageConstants.MESSAGE_TYPE_ORDER_UPDATED:
                handleOrderUpdated(orderMessage);
                break;
            case MessageConstants.MESSAGE_TYPE_ORDER_PAID:
                handleOrderPaid(orderMessage);
                break;
            case MessageConstants.MESSAGE_TYPE_ORDER_CANCELLED:
                handleOrderCancelled(orderMessage);
                break;
            default:
                log.warn("未知的订单消息类型: {}", orderMessage.getMessageType());
        }
        
        log.info("处理订单消息完成: messageType={}, orderId={}", 
                orderMessage.getMessageType(), orderMessage.getOrderId());
    }
    
    /**
     * 处理普通文本消息
     */
    private void processTextMessage(String message) {
        log.info("处理普通文本消息: {}", message);
        // 这里可以添加对普通文本消息的处理逻辑
    }
    
    /**
     * 处理订单创建消息
     */
    private void handleOrderCreated(OrderMessage orderMessage) {
        log.info("处理订单创建消息: orderId={}, orderNumber={}, userId={}, content={}", 
                orderMessage.getOrderId(), orderMessage.getOrderNumber(), 
                orderMessage.getUserId(), orderMessage.getContent());
        
        // 这里可以添加订单创建后的业务逻辑，比如：
        // 1. 发送邮件通知
        // 2. 更新统计数据
        // 3. 触发其他业务流程
    }
    
    /**
     * 处理订单状态更新消息
     */
    private void handleOrderUpdated(OrderMessage orderMessage) {
        log.info("处理订单状态更新消息: orderId={}, orderNumber={}, status={}, content={}", 
                orderMessage.getOrderId(), orderMessage.getOrderNumber(), 
                orderMessage.getOrderStatus(), orderMessage.getContent());
        
        // 这里可以添加订单状态更新后的业务逻辑
    }
    
    /**
     * 处理订单支付消息
     */
    private void handleOrderPaid(OrderMessage orderMessage) {
        log.info("处理订单支付消息: orderId={}, orderNumber={}, amount={}, content={}", 
                orderMessage.getOrderId(), orderMessage.getOrderNumber(), 
                orderMessage.getOrderAmount(), orderMessage.getContent());
        
        // 这里可以添加订单支付后的业务逻辑
    }
    
    /**
     * 处理订单取消消息
     */
    private void handleOrderCancelled(OrderMessage orderMessage) {
        log.info("处理订单取消消息: orderId={}, orderNumber={}, content={}", 
                orderMessage.getOrderId(), orderMessage.getOrderNumber(), 
                orderMessage.getContent());
        
        // 这里可以添加订单取消后的业务逻辑
    }
}
