# Account Service

账户服务是基于DDD（领域驱动设计）架构的微服务，负责管理用户账户，支持账户创建、余额扣减、余额增加、账户查询等功能。

## 功能特性

- ✅ 账户创建与管理
- ✅ 余额扣减与增加
- ✅ 账户查询与统计
- ✅ 账户状态管理（正常、冻结、关闭）
- ✅ 金额冻结与解冻
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
| POST | `/api/v1/account/debit` | 扣减账户余额（与order-service集成） |
| POST | `/api/v1/account/credit` | 增加账户余额 |
| GET  | `/api/v1/account/user/{userId}` | 查询账户信息 |
| GET  | `/api/v1/account/check` | 检查账户余额是否充足 |
| POST | `/api/v1/account/create` | 创建账户 |
| GET  | `/api/v1/account/active` | 查询活跃账户 |
| GET  | `/api/v1/account/frozen` | 查询冻结账户 |

### 扣减余额示例

```bash
curl -X POST "http://localhost:8083/api/v1/account/debit" \
  -d "userId=1001&amount=100.00"
```

### 查询账户示例

```bash
curl -X GET "http://localhost:8083/api/v1/account/user/1001"
```

## 数据库设计

### account表

| 字段 | 类型 | 描述 |
|------|------|------|
| id | BIGINT | 账户ID |
| user_id | BIGINT | 用户ID |
| balance | DECIMAL(15,2) | 账户余额 |
| frozen_amount | DECIMAL(15,2) | 冻结金额 |
| status | VARCHAR(20) | 账户状态 |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |

### account_transaction表

| 字段 | 类型 | 描述 |
|------|------|------|
| id | BIGINT | 交易ID |
| account_id | BIGINT | 账户ID |
| user_id | BIGINT | 用户ID |
| transaction_type | VARCHAR(20) | 交易类型 |
| amount | DECIMAL(15,2) | 交易金额 |
| balance_before | DECIMAL(15,2) | 交易前余额 |
| balance_after | DECIMAL(15,2) | 交易后余额 |
| business_no | VARCHAR(64) | 业务流水号 |
| reason | VARCHAR(255) | 交易原因 |
| create_time | TIMESTAMP | 创建时间 |

## 配置说明

### 应用配置 (application.yml)

```yaml
server:
  port: 8083

spring:
  application:
    name: account-service
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/account
    username: root
    password: 123

seata:
  tx-service-group: account_tx_group
```

## 部署运行

### 1. 数据库准备

```sql
CREATE DATABASE account;
USE account;

-- 执行 src/main/resources/db/migration/V1__Create_Account_Tables.sql
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
curl http://localhost:8083/api/v1/account/ping
```

## 错误码说明

| 错误码 | 描述 |
|--------|------|
| ACC-01-001 | 账户不存在 |
| ACC-01-002 | 账户已存在 |
| ACC-01-003 | 账户余额不足 |
| ACC-01-004 | 账户状态异常 |
| ACC-01-005 | 账户操作失败 |

## 业务规则

### 账户状态

- **ACTIVE**: 正常状态，可以进行所有操作
- **FROZEN**: 冻结状态，不能进行交易操作
- **CLOSED**: 关闭状态，不能进行任何操作
- **PENDING**: 待激活状态
- **CANCELLED**: 已注销状态

### 金额管理

- 支持多种货币，默认为人民币（CNY）
- 金额精度为2位小数
- 支持余额冻结和解冻功能
- 总余额 = 可用余额 + 冻结金额

### 交易类型

- **DEPOSIT**: 充值
- **WITHDRAW**: 提现
- **TRANSFER_OUT**: 转账转出
- **TRANSFER_IN**: 转账转入
- **CONSUME**: 消费
- **REFUND**: 退款
- **FREEZE**: 冻结
- **UNFREEZE**: 解冻

## 监控与运维

### 健康检查

```bash
curl http://localhost:8083/actuator/health
```

### API文档

访问 http://localhost:8083/swagger-ui.html

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

## 与order-service的集成

account-service完全兼容order-service的调用：

```java
// order-service中的调用
@FeignClient(name = "account-service", path = "/api/v1/account")
public interface AccountClient {
    @PostMapping("/debit")
    ApiResponse<Void> debitAccount(@RequestParam("userId") Long userId, 
                                 @RequestParam("amount") Double amount);
}
```

## 注意事项

1. 账户余额扣减操作支持分布式事务
2. 所有金额操作都有业务日志记录
3. 支持服务降级，确保系统稳定性
4. 建议配置适当的连接池参数
5. 金额计算使用BigDecimal避免精度问题
