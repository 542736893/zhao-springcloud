package com.zhao.order.infrastructure.remote.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {
    private final RestTemplate restTemplate;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void deduct(Long productId, Integer count) {
        String url = String.format("http://localhost:8082/api/v1/inventory/deduct?productId=%d&count=%d", productId, count);
        ResponseEntity<String> resp = restTemplate.postForEntity(url, null, String.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("inventory deduct failed");
        }
    }
}


