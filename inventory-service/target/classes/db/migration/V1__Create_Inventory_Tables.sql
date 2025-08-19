-- 创建库存表
CREATE TABLE IF NOT EXISTS `inventory` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '库存ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `total` INT NOT NULL DEFAULT 0 COMMENT '总库存',
    `used` INT NOT NULL DEFAULT 0 COMMENT '已使用库存',
    `residue` INT NOT NULL DEFAULT 0 COMMENT '剩余库存',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='库存表';

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
INSERT INTO `inventory` (`product_id`, `total`, `used`, `residue`) VALUES
(1001, 100, 0, 100),
(1002, 200, 10, 190),
(1003, 50, 5, 45),
(1004, 300, 50, 250),
(1005, 80, 20, 60);
