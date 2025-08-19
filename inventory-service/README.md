# Inventory Service

库存服务是基于DDD（领域驱动设计）架构的微服务，负责管理商品库存，支持库存扣减、恢复、查询等功能。

## 功能特性

- ✅ 库存扣减与恢复
- ✅ 库存查询与统计
- ✅ 低库存预警
- ✅ 缺货监控
- ✅ 分布式事务支持（Seata）
- ✅ 服务降级与熔断（Sentinel）
- ✅ 服务注册与发现（Nacos）
- ✅ API文档（Swagger）

## 技术栈

- **框架**: Spring Boot 3.4.8
- **微服务**: Spring Cloud
- **数据库**: MySQL 8.0
- **连接池**: Druid
- **ORM**: MyBatis-Plus
- **分布式事务**: Seata
- **服务治理**: Nacos
- **API文档**: Swagger/OpenAPI 3

## 架构设计

采用DDD四层架构：

```
├── interfaces          # 接口层：REST API、DTO、Feign接口
├── application         # 应用层：应用服务、命令查询、装配器
├── domain             # 领域层：聚合根、值对象、仓储接口
└── infrastructure     # 基础设施层：持久化、配置、远程调用
```

## API接口

### 核心接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/v1/inventory/deduct` | 扣减库存 |
| POST | `/api/v1/inventory/restore` | 恢复库存 |
| GET  | `/api/v1/inventory/product/{productId}` | 查询库存 |
| GET  | `/api/v1/inventory/check` | 检查库存是否充足 |
| GET  | `/api/v1/inventory/low-stock` | 查询低库存商品 |
| GET  | `/api/v1/inventory/out-of-stock` | 查询缺货商品 |

### 扣减库存示例

```bash
curl -X POST "http://localhost:8082/api/v1/inventory/deduct" \
  -d "productId=1001&quantity=10"
```

### 查询库存示例

```bash
curl -X GET "http://localhost:8082/api/v1/inventory/product/1001"
```

## 数据库设计

### inventory表

| 字段 | 类型 | 描述 |
|------|------|------|
| id | BIGINT | 库存ID |
| product_id | BIGINT | 商品ID |
| total | INT | 总库存 |
| used | INT | 已使用库存 |
| residue | INT | 剩余库存 |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |

## 配置说明

### 应用配置 (application.yml)

```yaml
server:
  port: 8082

spring:
  application:
    name: inventory-service
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/inventory
    username: root
    password: 123

seata:
  tx-service-group: inventory_tx_group
```

## 部署运行

### 1. 数据库准备

```sql
CREATE DATABASE inventory;
USE inventory;

-- 执行 src/main/resources/db/migration/V1__Create_Inventory_Tables.sql
```

### 2. 启动依赖服务

- MySQL 8.0
- Nacos Server

### 3. 启动应用

```bash
mvn spring-boot:run
```

### 4. 验证服务

```bash
curl http://localhost:8082/api/v1/inventory/ping
```

## 错误码说明

| 错误码 | 描述 |
|--------|------|
| INV-01-001 | 库存记录不存在 |
| INV-01-002 | 库存不足 |
| INV-01-003 | 库存操作失败 |
| INV-01-004 | 商品不存在 |
| INV-01-005 | 仓库不存在 |

## 监控与运维

### 健康检查

```bash
curl http://localhost:8082/actuator/health
```

### API文档

访问 http://localhost:8082/swagger-ui.html

## 开发指南

### 添加新功能

1. 在 `domain` 层定义业务逻辑
2. 在 `application` 层编排用例
3. 在 `infrastructure` 层实现技术细节
4. 在 `interfaces` 层暴露API

### 测试

```bash
mvn test
```

## 注意事项

1. 库存扣减操作支持分布式事务
2. 所有库存操作都有业务日志记录
3. 支持服务降级，确保系统稳定性
4. 建议配置适当的连接池参数
