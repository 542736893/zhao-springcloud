package com.zhao.order.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/rocketmq")
@RequiredArgsConstructor
public class RocketMQController {
    
    private final RocketMQTemplate rocketMQTemplate;
    
    /**
     * 发送普通消息
     */
    @PostMapping("/send")
    public String sendMessage(@RequestParam String topic, 
                             @RequestParam String message) {
        rocketMQTemplate.convertAndSend(topic, message);
        log.info("发送消息: topic={}, message={}, time={}", topic, message, LocalDateTime.now());
        return "消息发送成功: " + message;
    }
    
    /**
     * 发送延迟消息 - 不同延迟级别示例
     */
    @PostMapping("/send-delay")
    public String sendDelayMessage(@RequestParam String topic,
                                  @RequestParam String message,
                                  @RequestParam(required = false, defaultValue = "1") int delayLevel) {
        // RocketMQ延迟级别: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        // 对应的delayLevel为1-18
        Message<String> rocketMsg = MessageBuilder.withPayload(message)
                .setHeader("DELAY", delayLevel)
                .build();
        rocketMQTemplate.send(topic, rocketMsg);
        log.info("发送延迟消息: topic={}, message={}, delayLevel={}, time={}", 
                topic, message, delayLevel, LocalDateTime.now());
        return "延迟消息发送成功，延迟级别: " + delayLevel;
    }
    
    /**
     * 发送带有自定义属性的消息
     */
    @PostMapping("/send-with-tags")
    public String sendMessageWithTags(@RequestParam String topic,
                                     @RequestParam String tags,
                                     @RequestParam String message) {
        String destination = topic + ":" + tags;
        rocketMQTemplate.convertAndSend(destination, message);
        log.info("发送带标签消息: destination={}, message={}, time={}", 
                destination, message, LocalDateTime.now());
        return "带标签消息发送成功";
    }
    
    /**
     * 发送订单超时取消示例
     */
    @PostMapping("/order-timeout")
    public String sendOrderTimeoutMessage(@RequestParam Long orderId,
                                         @RequestParam(required = false, defaultValue = "16") int delayLevel) {
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("orderId", orderId);
        messageBody.put("createTime", LocalDateTime.now().toString());
        messageBody.put("type", "ORDER_TIMEOUT_CHECK");
        
        // 发送延迟消息，用于30分钟后检查订单是否超时
        Message<Map<String, Object>> rocketMsg = MessageBuilder.withPayload(messageBody)
                .setHeader("DELAY", delayLevel)
                .build();
        rocketMQTemplate.send("order-topic", rocketMsg);
        log.info("发送订单超时检查消息: orderId={}, delayLevel={}, time={}", 
                orderId, delayLevel, LocalDateTime.now());
        return "订单超时检查消息发送成功，订单ID: " + orderId;
    }
}