package com.zhao.order.application.service;

import com.zhao.common.exception.BusinessException;
import com.zhao.common.web.ErrorCode;
import com.zhao.order.application.command.CreateOrderCommand;
import com.zhao.order.domain.model.order.Order;
import com.zhao.order.domain.repository.OrderRepository;
import com.zhao.order.infrastructure.remote.account.AccountClient;
import com.zhao.order.infrastructure.remote.inventory.InventoryClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderAppService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final AccountClient accountClient;

    public OrderAppService(OrderRepository orderRepository,
                           InventoryClient inventoryClient,
                           AccountClient accountClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.accountClient = accountClient;
    }

    public Long createOrder(CreateOrderCommand cmd) {
        if (cmd.getUserId() == null || cmd.getProductId() == null ||
                cmd.getCount() == null || cmd.getAmount() == null) {
            throw new BusinessException(ErrorCode.SERVER_ERROR.getCode(), "参数不完整");
        }
        if (cmd.getCount() <= 0 || cmd.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.SERVER_ERROR.getCode(), "数量或金额非法");
        }
        Order order = Order.create(cmd.getUserId(), cmd.getProductId(), cmd.getCount(), cmd.getAmount());
        // 调用库存与账户服务（简化实现，无分布式事务）
        inventoryClient.deduct(cmd.getProductId(), cmd.getCount());
        accountClient.debit(cmd.getUserId(), cmd.getAmount());
        return orderRepository.save(order);
    }
}


