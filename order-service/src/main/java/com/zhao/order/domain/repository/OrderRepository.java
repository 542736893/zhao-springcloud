package com.zhao.order.domain.repository;

import com.zhao.order.domain.model.order.Order;

public interface OrderRepository {
    Long save(Order order);
}


