package com.zhao.order.infrastructure.remote.account;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountClient {
    private final RestTemplate restTemplate;

    public AccountClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void debit(Long userId, BigDecimal amount) {
        String url = String.format("http://localhost:8083/api/v1/account/debit?userId=%d&amount=%s", userId, amount.toPlainString());
        ResponseEntity<String> resp = restTemplate.postForEntity(url, null, String.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("account debit failed");
        }
    }
}


