# Order Service RocketMQ 功能测试说明

## 概述

本文档说明如何测试order-service中新增的RocketMQ消息发送和接收功能。

## 功能特性

### 1. 消息生产者
- 支持发送订单创建消息
- 支持发送订单状态更新消息  
- 支持发送订单支付消息
- 支持发送自定义消息

### 2. 消息消费者
- 自动消费订单相关消息
- 支持不同消息类型的分别处理
- 完整的日志记录和异常处理

### 3. 测试接口
提供了完整的REST API接口用于测试消息功能

## 配置说明

### RocketMQ配置 (application.yml)
```yaml
spring:
  cloud:
    rocketmq:
      name-server: rocketmq-namesrv:9876
      producer:
        group: ${spring.application.name}-producer
        send-message-timeout: 3000
        retry-times-when-send-failed: 2
        retry-times-when-send-async-failed: 2
```

### 消息常量
- **Topic**: `order-topic`
- **消费者组**: `order-consumer-group`
- **消息标签**:
  - `order-created`: 订单创建消息
  - `order-updated`: 订单状态更新消息
  - `order-paid`: 订单支付消息
  - `order-cancelled`: 订单取消消息

## API接口说明

### 1. 发送订单创建消息
```http
POST /api/v1/order/message/send-order-created
Content-Type: application/json

{
  "orderId": 12345,
  "orderNumber": "ORDER-20231205-001",
  "userId": 1001,
  "content": "订单创建成功",
  "orderStatus": "CREATED",
  "orderAmount": 99.99,
  "extInfo": "{\"source\":\"test\"}"
}
```

### 2. 发送订单状态更新消息
```http
POST /api/v1/order/message/send-order-updated
Content-Type: application/json

{
  "orderId": 12345,
  "orderNumber": "ORDER-20231205-001",
  "userId": 1001,
  "content": "订单状态已更新",
  "orderStatus": "PAID",
  "orderAmount": 99.99
}
```

### 3. 发送订单支付消息
```http
POST /api/v1/order/message/send-order-paid
Content-Type: application/json

{
  "orderId": 12345,
  "orderNumber": "ORDER-20231205-001",
  "userId": 1001,
  "content": "订单支付成功",
  "orderStatus": "PAID",
  "orderAmount": 99.99
}
```

### 4. 发送自定义消息
```http
POST /api/v1/order/message/send-custom
Content-Type: application/json

{
  "topic": "order-topic",
  "tag": "custom-tag",
  "content": "这是一条自定义消息",
  "key": "custom-key-001"
}
```

### 5. 模拟订单创建（简化接口）
```http
POST /api/v1/order/message/simulate-order-created?orderId=12345&orderNumber=ORDER-001&userId=1001
```

### 6. 模拟订单状态更新（简化接口）
```http
POST /api/v1/order/message/simulate-order-updated?orderId=12345&orderNumber=ORDER-001&userId=1001&status=PAID
```

### 7. 简单测试接口
```http
GET /api/v1/order/message/test-simple
```

## 测试步骤

### 1. 启动服务
确保以下服务已启动：
- RocketMQ NameServer
- RocketMQ Broker
- order-service

### 2. 发送测试消息
使用上述API接口发送测试消息，推荐从简单测试接口开始：

```bash
curl -X GET "http://localhost:8081/api/v1/order/message/test-simple"
```

### 3. 查看日志
观察order-service的日志输出，应该能看到：
- 消息发送成功的日志
- 消息接收和处理的日志

### 4. 验证消息流程
1. 发送消息后，检查生产者日志确认消息发送成功
2. 检查消费者日志确认消息被正确接收和处理
3. 验证不同类型消息的处理逻辑

## 日志示例

### 发送消息日志
```
2023-12-05 10:30:15.123 [main] INFO  c.z.o.a.s.OrderMessageService - 准备发送订单创建消息: orderId=12345, orderNumber=ORDER-001
2023-12-05 10:30:15.125 [main] INFO  c.z.o.i.m.OrderMessageProducer - 发送订单创建消息成功: orderId=12345, orderNumber=ORDER-001
```

### 接收消息日志
```
2023-12-05 10:30:15.130 [ConsumeMessageThread_1] INFO  c.z.o.i.m.OrderMessageConsumer - 接收到订单消息: {"orderId":12345,"orderNumber":"ORDER-001",...}
2023-12-05 10:30:15.132 [ConsumeMessageThread_1] INFO  c.z.o.i.m.OrderMessageConsumer - 处理订单创建消息: orderId=12345, orderNumber=ORDER-001, userId=1001
```

## 故障排查

### 1. 消息发送失败
- 检查RocketMQ服务是否正常运行
- 检查网络连接和配置
- 查看详细错误日志

### 2. 消息消费失败
- 检查消费者组配置
- 检查Topic和Tag配置
- 查看消费者异常日志

### 3. 常见问题
- 确保RocketMQ NameServer地址配置正确
- 确保Topic已创建（可自动创建）
- 检查消息序列化/反序列化是否正常

## 扩展功能

### 1. 添加新的消息类型
1. 在`MessageConstants`中添加新的常量
2. 在`OrderMessageProducer`中添加发送方法
3. 在`OrderMessageConsumer`中添加处理逻辑
4. 在`OrderMessageService`中添加业务方法
5. 在`MessageTestController`中添加测试接口

### 2. 消息持久化
可以考虑将重要消息存储到数据库中，用于审计和重试。

### 3. 消息监控
可以集成RocketMQ Console或其他监控工具来监控消息的发送和消费情况。

## 注意事项

1. 生产环境中应该配置合适的重试策略和死信队列
2. 消息内容应该包含足够的信息用于业务处理和问题排查
3. 建议对重要消息进行幂等性处理
4. 定期清理过期的消息和日志
