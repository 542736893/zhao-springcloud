package com.zhao.order.interfaces.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zhao.common.web.ApiResponse;
import com.zhao.order.application.service.OrderAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "测试接口", description = "测试接口")
public class TestController {

    private final OrderAppService orderAppService;

    @RequestMapping("/test/seata")
    @Operation(summary = "测试Seata", description = "使用分布式事务：本地插入订单 + 远程扣减库存与账户余额")
    @SentinelResource(value = "testSeata")
    public ApiResponse<String> testSeata(@RequestParam("userId") Long userId,
                                         @RequestParam("productId") Long productId,
                                         @RequestParam("quantity") Integer quantity,
                                         @RequestParam("unitPrice") Double unitPrice) {
        String result = orderAppService.testSeataTx(userId, productId, quantity, unitPrice);
        //获取当前容器服务实例名
        System.getenv("project.name");
        return ApiResponse.ok("testSeata:" + result);
    }
}
