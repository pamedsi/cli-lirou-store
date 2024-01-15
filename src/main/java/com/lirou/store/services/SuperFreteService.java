package com.lirou.store.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lirou.store.DTOs.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.DTOs.bodyForCalculateShipping.BodyForCalculateShipping;

import com.lirou.store.DTOs.bodyForCalculateShipping.PackageDimensions;
import com.lirou.store.DTOs.bodyForCalculateShipping.PostalCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class SuperFreteService {

    @Value("${token}")
    private String token;
    private final String baseURL = "https://sandbox.superfrete.com/api";

    public ResponseEntity<?> calculateShipping(String to) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String services =  "1,2,17";
        BodyForCalculateShipping body = new BodyForCalculateShipping(new PostalCode("54340070"), new PostalCode(to), services, new PackageDimensions(75, 11, 16, 0.3));
        String json = new ObjectMapper().writeValueAsString(body).replace("_dimensions", "");
        HttpHeaders headers = createHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);

        return restTemplate.exchange(baseURL + "/v0/calculator" , HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<?> sendShippingToSuperFrete(ShippingInfToSendToSuperFreteDTO body) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(body);
        HttpHeaders headers = createHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(baseURL + "/v0/cart" , HttpMethod.POST, requestEntity, String.class);
    }

    private HttpHeaders createHeaders () {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Lirou Store (liroustore@gmail.com)");
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
}