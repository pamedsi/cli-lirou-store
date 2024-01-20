package com.lirou.store.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lirou.store.DTOs.superfrete.*;
import com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.BodyForCalculateShipping;

import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.PackageDimensions;
import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.PostalCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class SuperFreteService {

    @Value("${token}")
    private String token;
    private final String baseURL = "https://sandbox.superfrete.com/api/v0";
    @Value("${CEP}")
    private String from;
    private HttpHeaders headers;

    public List<ShippingPricesDTO> calculateShipping(String to) {
        RestTemplate restTemplate = new RestTemplate();
        String services =  "1,2,17";
        BodyForCalculateShipping body = new BodyForCalculateShipping(new PostalCode(from), new PostalCode(removeNonDigits(to)), services, new PackageDimensions(75, 11, 16, 0.3));
        String jsonBody = new Gson().toJson(body).replace("_dimensions", "");
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonBody, headers);
        String jsonResponseBody = restTemplate.exchange(baseURL + "/calculator", HttpMethod.POST, requestEntity, String.class).getBody();

        Type pricesList = new TypeToken<List<SuperFretePackageDTO>>(){}.getType();
        List<SuperFretePackageDTO> responseBody = new Gson().fromJson(jsonResponseBody, pricesList);

        assert responseBody != null;
        return ShippingPricesDTO.severalToDTO(responseBody);
    }

    public ProtocolData sendShippingToSuperFrete(ShippingInfToSendToSuperFreteDTO body) {
        String json = new Gson().toJson(body);
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/cart" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, ProtocolData.class);
    }

    public ShippingOfOrderDTO finishOrderAndGeneratePrintableLabel(OrdersIDs orders){
        String json = new Gson().toJson(orders);
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/checkout" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, ShippingOfOrderDTO.class);
    }

    public DeliveryInfoDTO getDeliveryInfo(String orderID){
        HttpHeaders headers = createHeaders(true);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/order/info/" + orderID , HttpMethod.GET, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, DeliveryInfoDTO.class);
    }

    public PrintInfo getPrintableLabel(OrdersIDs ordersIDs){
        HttpHeaders headers = createHeaders(true);
        HttpEntity<?> requestEntity = new HttpEntity<>(ordersIDs, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/tag/print" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, PrintInfo.class);
    }

    public OrderCancellationResponse cancelOrder(AbortingRequestDTO requestBody){
        HttpHeaders headers = createHeaders(true);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<?> response = restTemplate.exchange(baseURL + "/order/cancel" , HttpMethod.POST, requestEntity, String.class);

        return new Gson().fromJson((String) response.getBody(), OrderCancellationResponse.class);
//        return new Gson().fromJson((String) response.getBody(), ErrorOnAborting.class);;
    }

    private HttpHeaders createHeaders(Boolean locally) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Lirou Store (liroustore@gmail.com)");
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
    @PostConstruct
    private void createHeaders(){
        headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Lirou Store (liroustore@gmail.com)");
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    public static String removeNonDigits(String cep) {
        return cep.replaceAll("[^0-9]", "");
    }

}