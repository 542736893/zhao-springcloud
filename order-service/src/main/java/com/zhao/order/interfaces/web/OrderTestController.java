package com.zhao.order.interfaces.web;

import com.zhao.common.web.ApiResponse;
import com.zhao.order.infrastructure.persistence.mapper.OrderMapper;
import com.zhao.order.infrastructure.persistence.po.OrderDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/order/test")
public class OrderTestController {

    private final OrderMapper orderMapper;


    public OrderTestController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @GetMapping("/insert")
    public ApiResponse<Long> insert() {
        OrderDO po = new OrderDO();
        po.setUserId(1L);
        po.setProductId(1001L);
        po.setCount(1);
        po.setAmount(new BigDecimal("99.00"));
        po.setStatus("CREATED");
        orderMapper.insert(po);
        return ApiResponse.ok(po.getId());
    }
    
}


