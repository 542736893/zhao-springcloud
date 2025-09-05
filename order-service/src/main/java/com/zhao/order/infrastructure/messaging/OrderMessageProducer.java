package com.zhao.order.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.order.domain.constants.MessageConstants;
import com.zhao.order.interfaces.dto.message.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 订单消息生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageProducer {
    
    private final RocketMQTemplate rocketMQTemplate;
    private final ObjectMapper objectMapper;
    
    /**
     * 发送订单创建消息
     */
    public void sendOrderCreatedMessage(OrderMessage orderMessage) {
        try {
            String destination = MessageConstants.ORDER_TOPIC + ":" + MessageConstants.ORDER_CREATED_TAG;
            String messageBody = objectMapper.writeValueAsString(orderMessage);
            
            rocketMQTemplate.convertAndSend(destination, messageBody);
            
            log.info("发送订单创建消息成功: orderId={}, orderNumber={}", 
                    orderMessage.getOrderId(), orderMessage.getOrderNumber());
        } catch (JsonProcessingException e) {
            log.error("发送订单创建消息失败，JSON序列化异常: {}", e.getMessage(), e);
            throw new RuntimeException("发送订单创建消息失败", e);
        } catch (Exception e) {
            log.error("发送订单创建消息失败: {}", e.getMessage(), e);
            throw new RuntimeException("发送订单创建消息失败", e);
        }
    }
    
    /**
     * 发送订单状态更新消息
     */
    public void sendOrderUpdatedMessage(OrderMessage orderMessage) {
        try {
            String destination = MessageConstants.ORDER_TOPIC + ":" + MessageConstants.ORDER_UPDATED_TAG;
            String messageBody = objectMapper.writeValueAsString(orderMessage);
            
            rocketMQTemplate.convertAndSend(destination, messageBody);
            
            log.info("发送订单状态更新消息成功: orderId={}, orderNumber={}, status={}", 
                    orderMessage.getOrderId(), orderMessage.getOrderNumber(), orderMessage.getOrderStatus());
        } catch (JsonProcessingException e) {
            log.error("发送订单状态更新消息失败，JSON序列化异常: {}", e.getMessage(), e);
            throw new RuntimeException("发送订单状态更新消息失败", e);
        } catch (Exception e) {
            log.error("发送订单状态更新消息失败: {}", e.getMessage(), e);
            throw new RuntimeException("发送订单状态更新消息失败", e);
        }
    }
    
    /**
     * 发送订单支付消息
     */
    public void sendOrderPaidMessage(OrderMessage orderMessage) {
        try {
            String destination = MessageConstants.ORDER_TOPIC + ":" + MessageConstants.ORDER_PAID_TAG;
            String messageBody = objectMapper.writeValueAsString(orderMessage);
            
            rocketMQTemplate.convertAndSend(destination, messageBody);
            
            log.info("发送订单支付消息成功: orderId={}, orderNumber={}, amount={}", 
                    orderMessage.getOrderId(), orderMessage.getOrderNumber(), orderMessage.getOrderAmount());
        } catch (JsonProcessingException e) {
            log.error("发送订单支付消息失败，JSON序列化异常: {}", e.getMessage(), e);
            throw new RuntimeException("发送订单支付消息失败", e);
        } catch (Exception e) {
            log.error("发送订单支付消息失败: {}", e.getMessage(), e);
            throw new RuntimeException("发送订单支付消息失败", e);
        }
    }
    
    /**
     * 发送自定义消息
     */
    public void sendCustomMessage(String topic, String tag, String content, String key) {
        try {
            String destination = topic + (tag != null ? ":" + tag : "");
            
            if (key != null && !key.trim().isEmpty()) {
                // 使用MessageBuilder构建带key的消息
                org.springframework.messaging.Message<String> message = MessageBuilder
                        .withPayload(content)
                        .setHeader("KEYS", key)
                        .build();
                rocketMQTemplate.send(destination, message);
            } else {
                rocketMQTemplate.convertAndSend(destination, content);
            }
            
            log.info("发送自定义消息成功: topic={}, tag={}, key={}", topic, tag, key);
        } catch (Exception e) {
            log.error("发送自定义消息失败: topic={}, tag={}, error={}", topic, tag, e.getMessage(), e);
            throw new RuntimeException("发送自定义消息失败", e);
        }
    }
}
