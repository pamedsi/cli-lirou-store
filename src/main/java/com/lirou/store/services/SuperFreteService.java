package com.lirou.store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public ResponseEntity<?> calculateShipping(String from, String to) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://sandbox.superfrete.com/api/v0/calculator";
        String services =  "1,2,17";
        BodyForCalculateShipping body = new BodyForCalculateShipping(new PostalCode(from), new PostalCode(to), services, new PackageDimensions(75, 11, 16, 0.3));
        String json = new ObjectMapper().writeValueAsString(body).replace("_dimensions", "");
        System.out.println(json);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Lirou Store (liroustore@gmail.com)");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    public ResponseEntity<?> generateTag(){
        return null;
    }
}