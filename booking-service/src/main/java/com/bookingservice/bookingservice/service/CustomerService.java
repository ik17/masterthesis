package com.bookingservice.bookingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class CustomerService {

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "customerService", fallbackMethod = "fallbackMethod")
    @Bulkhead(name = "customerService", fallbackMethod = "fallbackMethod", type = Bulkhead.Type.SEMAPHORE)
    @Retry(name = "customerService", fallbackMethod = "fallbackMethod")
    public Integer getUserId(String userIdentifier) throws JsonProcessingException {
        final String uri = "http://localhost:8082/customer/findByIdentifier/" + userIdentifier;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(userIdentifier);
        Map<String,Object> map = mapper.readValue(result, Map.class);

        return Integer.parseInt(map.get("customerId").toString());
    }

    public Integer fallbackMethod(String userIdentifier, Exception exception) {
        System.out.println("Received exception while using identifier: " + userIdentifier + " " + exception.getMessage());
        return 0;
    }
}
