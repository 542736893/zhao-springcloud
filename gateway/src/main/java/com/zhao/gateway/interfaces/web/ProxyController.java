package com.zhao.gateway.interfaces.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/order/**")
    public ResponseEntity<String> proxyOrder(HttpMethod method, HttpServletRequest request) throws IOException {
        return forward(method, request, "http://order-service:8081");
    }

    @RequestMapping(value = "/inventory/**")
    public ResponseEntity<String> proxyInventory(HttpMethod method, HttpServletRequest request) throws IOException {
        return forward(method, request, "http://inventory-service:8082");
    }

    @RequestMapping(value = "/account/**")
    public ResponseEntity<String> proxyAccount(HttpMethod method, HttpServletRequest request) throws IOException {
        return forward(method, request, "http://account-service:8083");
    }

    private ResponseEntity<String> forward(HttpMethod method, HttpServletRequest request, String targetBase) throws IOException {
        String path = request.getRequestURI().replaceFirst("/api/(order|inventory|account)", "");
        String query = request.getQueryString();
        String url = targetBase + path + (query != null ? ("?" + query) : "");
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        return restTemplate.exchange(url, method, null, String.class);
    }
}


