# Zhao Spring Cloud 微服务项目

基于 Spring Cloud 的微服务架构项目，采用 DDD（领域驱动设计）架构模式。

## 项目架构

```
zhao-springcloud
├── zhao-dependencies    # 父 BOM（统一三方依赖版本）
├── zhao-parent          # 聚合父（声明 modules 与 pluginManagement）
├── common               # 公共模块（工具类、通用DTO、异常等）
├── order-service        # 订单服务
├── inventory-service    # 库存服务
├── account-service      # 账户服务
└── gateway              # 网关服务
```

## 技术栈

- **JDK**: 17
- **Spring Boot**: 3.4.8
- **Spring Cloud**: 2024.0.2
- **Spring Cloud Alibaba**: 2023.0.1.0
- **Nacos**: 注册中心与配置中心
- **OpenFeign**: 服务间调用
- **Sentinel**: 服务容错与限流
- **Seata**: 分布式事务
- **MyBatis Plus**: 数据访问层
- **MySQL**: 8.0 数据库
- **Druid**: 数据库连接池

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Docker & Docker Compose

### 1. 启动基础设施服务

```bash
# 启动 Nacos
docker compose -f nacos-compose.yml up -d

# 启动 MySQL（如果本地没有）
docker compose -f mysql-compose.yml up -d
```

### 2. 创建数据库

```sql
-- 创建订单数据库
CREATE DATABASE `order` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 创建库存数据库
CREATE DATABASE `inventory` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 创建账户数据库
CREATE DATABASE `account` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 3. 编译项目

```bash
mvn -f zhao-parent/pom.xml -DskipTests clean package
```

### 4. 启动服务

```bash
# 启动所有服务
docker compose up -d --build

# 查看服务状态
docker compose ps
```

### 5. 验证服务

- 网关: `GET http://localhost:8080/api/v1/gateway/ping`
- 订单: `GET http://localhost:8081/api/v1/order/ping`
- 库存: `GET http://localhost:8082/api/v1/inventory/ping`
- 账户: `GET http://localhost:8083/api/v1/account/ping`

## 项目特性

### DDD 架构设计

- **应用层**: 编排业务用例，协调领域对象
- **领域层**: 核心业务逻辑，领域模型，业务规则
- **基础设施层**: 技术实现细节，外部系统集成
- **接口层**: 对外暴露API，处理用户请求

### 微服务特性

- 服务注册与发现（Nacos）
- 配置中心（Nacos）
- 服务间调用（OpenFeign）
- 分布式事务（Seata）
- 服务容错与限流（Sentinel）
- API网关（Spring Cloud Gateway）

### 数据访问

- MyBatis Plus 增强
- 数据库连接池（Druid）
- 分页查询支持
- 逻辑删除支持

## 开发指南

### 添加新服务

1. 在 `zhao-parent/pom.xml` 中添加模块
2. 创建服务目录结构
3. 实现 DDD 分层架构
4. 配置服务注册和配置

### 添加新接口

1. 在 `interfaces.web` 包下创建 Controller
2. 在 `application.service` 包下创建应用服务
3. 在 `domain` 包下定义领域模型
4. 在 `infrastructure.persistence` 包下实现数据访问

### 配置管理

- 使用 Nacos 配置中心管理配置
- 支持多环境配置（dev/test/prod）
- 配置热更新支持

## 部署说明

### 生产环境

1. 修改配置文件中的数据库连接信息
2. 配置 Nacos 生产环境地址
3. 调整 JVM 参数和连接池配置
4. 配置监控和日志收集

### Docker 部署

```bash
# 构建镜像
docker build -t zhao-order-service:latest order-service/

# 运行容器
docker run -d -p 8081:8081 --name order-service zhao-order-service:latest
```

## 监控与运维

### 健康检查

- Spring Boot Actuator 健康端点
- 自定义健康检查指标
- 服务状态监控

### 日志管理

- 结构化日志输出
- 请求追踪ID
- 日志级别配置

### 性能监控

- JVM 指标监控
- 数据库连接池监控
- 接口响应时间监控

## 常见问题

### 1. 服务启动失败

- 检查 Nacos 服务是否正常
- 验证数据库连接配置
- 查看启动日志错误信息

### 2. 分布式事务失败

- 检查 Seata 配置
- 验证数据库 undo_log 表
- 查看事务日志

### 3. 服务调用失败

- 检查服务注册状态
- 验证 Feign 客户端配置
- 查看服务调用日志

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交代码变更
4. 创建 Pull Request

## 许可证

MIT License

## 联系方式

- 项目维护者: Zhao
- 邮箱: zhao@example.com
- 项目地址: https://github.com/zhao/zhao-springcloud
