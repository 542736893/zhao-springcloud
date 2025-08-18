package com.zhao.order.interfaces.web;

import com.zhao.common.web.ApiResponse;
import com.zhao.order.application.command.CreateOrderCommand;
import com.zhao.order.application.service.OrderAppService;
import com.zhao.order.interfaces.dto.CreateOrderRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderAppService orderAppService;

    public OrderController(OrderAppService orderAppService) {
        this.orderAppService = orderAppService;
    }

    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("pong");
    }

    @PostMapping("/create")
    public ApiResponse<Long> create(@RequestBody CreateOrderRequest req) {
        CreateOrderCommand cmd = new CreateOrderCommand();
        cmd.setUserId(req.getUserId());
        cmd.setProductId(req.getProductId());
        cmd.setCount(req.getCount());
        cmd.setAmount(req.getAmount());
        Long id = orderAppService.createOrder(cmd);
        return ApiResponse.ok(id);
    }
}


