package com.zhao.order.application.assembler;

import com.zhao.order.application.command.CreateOrderCommand;
import com.zhao.order.domain.model.order.Order;
import com.zhao.order.domain.model.order.OrderStatus;
import com.zhao.order.interfaces.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderAssembler {
    
    public Order toDomain(CreateOrderCommand command) {
        // 由于CreateOrderCommand结构已改变，这里需要重新实现
        // 暂时返回null，需要根据新的DDD结构重新实现
        throw new UnsupportedOperationException("需要根据新的DDD结构重新实现");
    }
    
    public OrderResponse toResponse(Order domain) {
        return OrderResponse.fromOrder(domain);
    }
}
