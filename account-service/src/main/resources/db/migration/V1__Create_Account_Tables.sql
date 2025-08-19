-- 创建账户表
CREATE TABLE IF NOT EXISTS `account` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '账户ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `balance` DECIMAL(15,2) NOT NULL DEFAULT 0.00 COMMENT '账户余额',
    `frozen_amount` DECIMAL(15,2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额',
    `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '账户状态',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_user_id` (`user_id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_balance` (`balance`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账户表';

-- 创建账户交易记录表
CREATE TABLE IF NOT EXISTS `account_transaction` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '交易ID',
    `account_id` BIGINT NOT NULL COMMENT '账户ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `transaction_type` VARCHAR(20) NOT NULL COMMENT '交易类型',
    `amount` DECIMAL(15,2) NOT NULL COMMENT '交易金额',
    `balance_before` DECIMAL(15,2) NOT NULL COMMENT '交易前余额',
    `balance_after` DECIMAL(15,2) NOT NULL COMMENT '交易后余额',
    `business_no` VARCHAR(64) COMMENT '业务流水号',
    `reason` VARCHAR(255) COMMENT '交易原因',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_account_id` (`account_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_transaction_type` (`transaction_type`),
    INDEX `idx_business_no` (`business_no`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账户交易记录表';

-- 创建Seata undo_log表
CREATE TABLE IF NOT EXISTS `undo_log` (
    `branch_id` BIGINT NOT NULL COMMENT 'branch transaction id',
    `xid` VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context` VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB NOT NULL COMMENT 'rollback info',
    `log_status` INT NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created` DATETIME(6) NOT NULL COMMENT 'create datetime',
    `log_modified` DATETIME(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='AT transaction mode undo table';

-- 插入测试数据
INSERT INTO `account` (`user_id`, `balance`, `frozen_amount`, `status`) VALUES
(1001, 1000.00, 0.00, 'ACTIVE'),
(1002, 2000.00, 100.00, 'ACTIVE'),
(1003, 500.00, 0.00, 'ACTIVE'),
(1004, 3000.00, 200.00, 'ACTIVE'),
(1005, 800.00, 50.00, 'FROZEN');
