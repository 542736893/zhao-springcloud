package com.zhao.order.application.service;

import com.zhao.order.application.command.CreateOrderCommand;
import com.zhao.order.domain.factory.OrderFactory;
import com.zhao.order.domain.model.aggregate.Order;
import com.zhao.order.domain.model.valueobject.OrderId;
import com.zhao.order.domain.model.valueobject.OrderNumber;
import com.zhao.order.domain.model.valueobject.UserId;
import com.zhao.order.domain.repository.OrderRepository;
import com.zhao.order.infrastructure.remote.account.AccountClient;
import com.zhao.order.infrastructure.remote.inventory.InventoryClient;
import com.zhao.order.interfaces.dto.response.OrderResponse;
import com.zhao.order.interfaces.assembler.OrderAssembler;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单应用服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderAppService {
    
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;
    private final OrderAssembler orderAssembler;
    private final InventoryClient inventoryClient;
    private final AccountClient accountClient;
    
    /**
     * 创建订单
     */
    @GlobalTransactional(timeoutMills = 60000, name = "create-order")
    public OrderResponse createOrder(CreateOrderCommand command) {
        log.info("开始创建订单，用户ID: {}, 订单项数量: {}", command.getUserId(), command.getItems().size());
        
        try {
            // 1. 创建订单领域对象
            Order order = orderFactory.createFromCommand(command);
            
            // 2. 保存订单（草稿状态）
            order = orderRepository.save(order);
            log.info("订单创建成功，订单ID: {}, 订单号: {}", order.getId().getValue(), order.getOrderNumber().getValue());
            
            // 3. 调用库存服务扣减库存
            deductInventory(command);
            
            // 4. 调用账户服务扣减余额
            deductAccountBalance(command);
            
            // 5. 确认订单
            order.confirm();
            order = orderRepository.save(order);
            
            log.info("订单确认成功，订单ID: {}, 状态: {}", order.getId().getValue(), order.getStatus());
            
            return orderAssembler.toResponse(order);
            
        } catch (Exception e) {
            log.error("创建订单失败，用户ID: {}, 错误: {}", command.getUserId(), e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 根据ID查询订单
     */
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderId));
        return orderAssembler.toResponse(order);
    }
    
    /**
     * 根据订单号查询订单
     */
    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(OrderNumber.of(orderNumber))
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderNumber));
        return orderAssembler.toResponse(order);
    }
    
    /**
     * 根据用户ID查询订单列表
     */
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(UserId.of(userId));
        return orders.stream()
                .map(orderAssembler::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 取消订单
     */
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderId));
        
        if (!order.canCancel()) {
            throw new IllegalStateException("订单不能取消，当前状态: " + order.getStatus());
        }
        
        order.cancel();
        orderRepository.save(order);
        log.info("订单取消成功，订单ID: {}", orderId);
    }
    
    /**
     * 支付订单
     */
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderId));
        
        if (!order.canPay()) {
            throw new IllegalStateException("订单不能支付，当前状态: " + order.getStatus());
        }
        
        order.pay();
        orderRepository.save(order);
        log.info("订单支付成功，订单ID: {}", orderId);
    }
    
    /**
     * 发货
     */
    public void shipOrder(Long orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderId));
        
        if (!order.canShip()) {
            throw new IllegalStateException("订单不能发货，当前状态: " + order.getStatus());
        }
        
        order.ship();
        orderRepository.save(order);
        log.info("订单发货成功，订单ID: {}", orderId);
    }
    
    /**
     * 完成订单
     */
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("订单不存在: " + orderId));
        
        if (!order.canComplete()) {
            throw new IllegalStateException("订单不能完成，当前状态: " + order.getStatus());
        }
        
        order.complete();
        orderRepository.save(order);
        log.info("订单完成成功，订单ID: {}", orderId);
    }

    /**
     * 测试分布式事务：本地插入订单 + 远程扣减库存与账户余额。
     * 成功则提交，任一环节失败抛出异常触发 Seata 全局回滚。
     */
    @GlobalTransactional(timeoutMills = 60000, name = "test-seata-tx")
    public String testSeataTx(Long userId, Long productId, int quantity, double unitPrice) {
        log.info("[testSeataTx] 开始，userId={}, productId={}, quantity={}, unitPrice={}", userId, productId, quantity, unitPrice);

        // 1) 组装最小下单命令
        CreateOrderCommand cmd = new CreateOrderCommand();
        cmd.setUserId(userId);
        cmd.setProvince("TestProvince");
        cmd.setCity("TestCity");
        cmd.setDistrict("TestDistrict");
        cmd.setDetailAddress("Test Street 001");
        cmd.setReceiverName("TestReceiver");
        cmd.setReceiverPhone("13800000000");
        cmd.setZipCode("000000");

        CreateOrderCommand.CreateOrderItemCommand item = new CreateOrderCommand.CreateOrderItemCommand();
        item.setProductId(productId);
        item.setProductName("TestProduct");
        item.setProductImage(null);
        item.setUnitPrice(String.valueOf(unitPrice));
        item.setQuantity(quantity);

        List<CreateOrderCommand.CreateOrderItemCommand> items = new ArrayList<>();
        items.add(item);
        cmd.setItems(items);

        try {
            // 2) 本地插入订单（简单保存）
            Order order = orderFactory.createFromCommand(cmd);
            orderRepository.simpleSave(order);
            log.info("[testSeataTx] 本地订单插入完成，订单号={}", order.getOrderNumber().getValue());

            // 3) 远程扣减库存
            var invResp = inventoryClient.deductInventory(productId, quantity);
            if (invResp == null || !Boolean.TRUE.equals(invResp.isSuccess())) {
                throw new RuntimeException("库存服务扣减失败");
            }
            log.info("[testSeataTx] 库存扣减完成");

            // 4) 远程扣减账户余额
            double totalAmount = unitPrice * quantity;
            var accResp = accountClient.debitAccount(userId, totalAmount);
            if (accResp == null || !Boolean.TRUE.equals(accResp.isSuccess())) {
                throw new RuntimeException("账户服务扣减失败");
            }
            log.info("[testSeataTx] 账户扣减完成");

            // 5) 成功
            log.info("[testSeataTx] 成功，提交事务");
            return "ok";
        } catch (Exception ex) {
            log.error("[testSeataTx] 失败，将回滚: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
    
    /**
     * 扣减库存
     */
    private void deductInventory(CreateOrderCommand command) {
        try {
            command.getItems().forEach(item -> {
                inventoryClient.deductInventory(item.getProductId(), item.getQuantity());
            });
            log.info("库存扣减成功");
        } catch (Exception e) {
            log.error("库存扣减失败: {}", e.getMessage());
            throw new RuntimeException("库存扣减失败", e);
        }
    }
    
    /**
     * 扣减账户余额
     */
    private void deductAccountBalance(CreateOrderCommand command) {
        try {
            // 计算总金额
            double totalAmount = command.getItems().stream()
                    .mapToDouble(item -> Double.parseDouble(item.getUnitPrice()) * item.getQuantity())
                    .sum();
            
            accountClient.debitAccount(command.getUserId(), totalAmount);
            log.info("账户余额扣减成功，用户ID: {}, 金额: {}", command.getUserId(), totalAmount);
        } catch (Exception e) {
            log.error("账户余额扣减失败: {}", e.getMessage());
            throw new RuntimeException("账户余额扣减失败", e);
        }
    }
}


