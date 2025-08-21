package com.zhao.gateway.interfaces.web;

/**
 * 已禁用的基于 Servlet 的代理控制器。
 * Spring Cloud Gateway 使用响应式路由，不应在网关中使用 Servlet API 或 RestTemplate 进行转发。
 * 如需自定义过滤或路由，请使用 GatewayFilter 或 RouteLocator。
 */
class ProxyController {
    // intentionally empty
}


