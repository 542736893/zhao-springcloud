package com.zhao.order.infrastructure.persistence.repository;

import com.zhao.order.domain.model.order.Order;
import com.zhao.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepositoryInMemory implements OrderRepository {
    private final AtomicLong idGen = new AtomicLong(1);
    private final Map<Long, Order> store = new ConcurrentHashMap<>();

    @Override
    public Long save(Order order) {
        Long id = idGen.getAndIncrement();
        order.setId(id);
        store.put(id, order);
        return id;
    }
}


