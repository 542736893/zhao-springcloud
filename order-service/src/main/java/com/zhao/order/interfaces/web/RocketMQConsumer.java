package com.zhao.order.interfaces.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
// @Component  // 临时禁用RocketMQ Consumer
// @RocketMQMessageListener(
//     topic = "order-topic",
//     consumerGroup = "order-service-consumer-group"
// )
public class RocketMQConsumer implements RocketMQListener<Object> {
    
    @Override
    public void onMessage(Object message) {
        log.info("收到消息: {}, 处理时间: {}", message, LocalDateTime.now());
        
        // 处理订单超时逻辑
        if (message instanceof Map) {
            Map<String, Object> messageMap = (Map<String, Object>) message;
            if ("ORDER_TIMEOUT_CHECK".equals(messageMap.get("type"))) {
                Long orderId = (Long) messageMap.get("orderId");
                String createTime = (String) messageMap.get("createTime");
                
                log.info("开始处理订单超时检查: orderId={}, createTime={}", orderId, createTime);
                
                // 这里可以添加实际的订单超时处理逻辑
                // 比如检查订单状态，如果未支付则取消订单
                checkAndCancelOrder(orderId);
            }
        } else if (message instanceof String) {
            log.info("收到字符串消息: {}", message);
        }
    }
    
    /**
     * 检查并取消超时订单
     * @param orderId 订单ID
     */
    private void checkAndCancelOrder(Long orderId) {
        log.info("检查订单状态并处理超时订单: orderId={}", orderId);
        
        // 实际业务逻辑：
        // 1. 查询订单状态
        // 2. 如果订单未支付且已超时，则取消订单
        // 3. 更新库存
        // 4. 其他相关业务处理
        
        // 模拟处理逻辑
        try {
            Thread.sleep(1000); // 模拟处理耗时
            log.info("订单超时处理完成: orderId={}", orderId);
        } catch (InterruptedException e) {
            log.error("处理订单超时异常: orderId={}", orderId, e);
            Thread.currentThread().interrupt();
        }
    }
}