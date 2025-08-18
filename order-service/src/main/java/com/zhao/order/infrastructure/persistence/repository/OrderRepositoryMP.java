package com.zhao.order.infrastructure.persistence.repository;

import com.zhao.order.domain.model.order.Order;
import com.zhao.order.domain.repository.OrderRepository;
import com.zhao.order.infrastructure.persistence.mapper.OrderMapper;
import com.zhao.order.infrastructure.persistence.po.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
@org.springframework.context.annotation.Primary
public class OrderRepositoryMP implements OrderRepository {

    private final OrderMapper orderMapper;

    public OrderRepositoryMP(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Long save(Order order) {
        OrderDO po = new OrderDO();
        po.setUserId(order.getUserId());
        po.setProductId(order.getProductId());
        po.setCount(order.getCount());
        po.setAmount(order.getAmount());
        po.setStatus(order.getStatus().name());
        orderMapper.insert(po);
        return po.getId();
    }
}


