package com.lirou.store.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lirou.store.DTOs.superfrete.ProtocolData;
import com.lirou.store.DTOs.superfrete.ShippingPricesDTO;
import com.lirou.store.DTOs.superfrete.SuperFretePackageDTO;
import com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.BodyForCalculateShipping;

import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.PackageDimensions;
import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.PostalCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class SuperFreteService {

    @Value("${token}")
    private String token;
    private final String baseURL = "https://sandbox.superfrete.com/api";

    public List<ShippingPricesDTO> calculateShipping(String to) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String services =  "1,2,17";
        BodyForCalculateShipping body = new BodyForCalculateShipping(new PostalCode("54340070"), new PostalCode(to), services, new PackageDimensions(75, 11, 16, 0.3));
        String jsonBody = new ObjectMapper().writeValueAsString(body).replace("_dimensions", "");
        HttpHeaders headers = createHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonBody, headers);

        String jsonResponseBody = restTemplate.exchange(baseURL + "/v0/calculator", HttpMethod.POST, requestEntity, String.class).getBody();

        Type pricesList = new TypeToken<List<SuperFretePackageDTO>>(){}.getType();
        List<SuperFretePackageDTO> responseBody = new Gson().fromJson(jsonResponseBody, pricesList);

        assert responseBody != null;
        return ShippingPricesDTO.severalToDTO(responseBody);
    }

    public ProtocolData sendShippingToSuperFrete(ShippingInfToSendToSuperFreteDTO body) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(body);
        HttpHeaders headers = createHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/v0/cart" , HttpMethod.POST, requestEntity, String.class).getBody();

        return new Gson().fromJson(responseBody, ProtocolData.class);

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