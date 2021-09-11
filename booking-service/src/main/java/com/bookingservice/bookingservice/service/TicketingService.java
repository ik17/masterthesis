package com.bookingservice.bookingservice.service;

import com.bookingservice.bookingservice.entity.Ticket;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketingService {

    @CircuitBreaker(name = "ticketingService")
    @Bulkhead(name = "ticketingService")
    @Retry(name = "ticketingService")
    @RateLimiter(name = "ticketingService")
    public List<Ticket> getUserTickets(Integer userId) {
        final String uri = "http://localhost:8083/ticketreservation/customer/" + userId;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        JSONArray jsonArray = new JSONArray(result);

        List<Ticket> resultList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length() ; i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            JSONObject object1 = new JSONObject(object.get("event").toString());
            resultList.add(new Ticket(object.get("ticketReservationId").toString(),
                    object1.get("eventId").toString(), object1.get("eventName").toString(), object.get("numberOfTickets").toString()));
        }
        return resultList;
    }

    @CircuitBreaker(name = "ticketingService")
    @Bulkhead(name = "ticketingService")
    @Retry(name = "ticketingService")
    @RateLimiter(name = "ticketingService")
    public ResponseEntity<String> makeReservation(Integer customerId, Integer eventId, Integer numberOfTickets) {
        final String uri = "http://localhost:8083/ticketreservation/";
        final String jsonString = new JSONObject()
                .put("userIdentification", customerId)
                .put("event", new JSONObject().put("eventId", eventId))
                .put("numberOfTickets", numberOfTickets).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonString ,headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(uri, entity, String.class);
    }

    @CircuitBreaker(name = "ticketingService")
    @Bulkhead(name = "ticketingService")
    @Retry(name = "ticketingService")
    @RateLimiter(name = "ticketingService")
    public boolean deleteTicket(Integer ticketReservationId) {
        final String uri = "http://localhost:8083/ticketreservation/" + ticketReservationId.toString();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri);
        return true;
    }
}
