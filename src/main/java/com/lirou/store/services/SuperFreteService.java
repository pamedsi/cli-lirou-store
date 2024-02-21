package com.lirou.store.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.lirou.store.models.superfrete.*;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.models.superfrete.bodyForCalculateShipping.BodyForCalculateShipping;

import com.lirou.store.models.superfrete.bodyForCalculateShipping.PackageDimensions;
import com.lirou.store.models.superfrete.bodyForCalculateShipping.PostalCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.BadRequestException;
import java.lang.reflect.Type;
import java.util.List;

import static com.lirou.store.validation.PostalCodeValidator.isAValidePostalCode;

@Service
public class SuperFreteService {

    @Value("${token}")
    private String token;
    @Value("${SUPER_FRETE_URL}")
    private String baseURL;
    @Value("${CEP}")
    private String from;
    private HttpHeaders headers;

    public List<ShippingPricesDTO> calculateShipping(String to) {
        if (!isAValidePostalCode(to)) throw new BadRequestException("CEP inválido!");

        RestTemplate restTemplate = new RestTemplate();
        String services =  "1,2,17";
        BodyForCalculateShipping body = new BodyForCalculateShipping(new PostalCode(from), new PostalCode(removeNonDigits(to)), services, new PackageDimensions(75, 11, 16, 0.3));
        String jsonBody = new Gson().toJson(body).replace("_dimensions", "");
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonBody, headers);
        String jsonResponseBody = restTemplate.exchange(baseURL + "/api/v0/calculator", HttpMethod.POST, requestEntity, String.class).getBody();

        Type pricesList = new TypeToken<List<SuperFretePackageDTO>>(){}.getType();
        List<SuperFretePackageDTO> responseBody = new Gson().fromJson(jsonResponseBody, pricesList);

        assert responseBody != null;
        return ShippingPricesDTO.severalToDTO(responseBody);
    }

    public ProtocolData sendShippingToSuperFrete(ShippingInfToSendToSuperFreteDTO body) {
        String json = new Gson().toJson(body);
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/api/v0/cart" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, ProtocolData.class);
    }

    public ShippingOfOrderDTO finishOrderAndGeneratePrintableLabel(OrdersIDs orders){
        String json = new Gson().toJson(orders);
        HttpEntity<?> requestEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/api/v0/checkout" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, ShippingOfOrderDTO.class);
    }

    public DeliveryInfoDTO getDeliveryInfo(String orderID){
        HttpHeaders headers = createHeaders(true);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/api/v0/order/info/" + orderID , HttpMethod.GET, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, DeliveryInfoDTO.class);
    }

    public PrintInfo getPrintableLabel(OrdersIDs ordersIDs){
        HttpHeaders headers = createHeaders(true);
        HttpEntity<?> requestEntity = new HttpEntity<>(ordersIDs, headers);
        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(baseURL + "/api/v0/tag/print" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, PrintInfo.class);
    }

    public OrderCancellationResponse cancelOrder(AbortingRequestDTO requestBody){
        HttpHeaders headers = createHeaders(true);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<?> response = restTemplate.exchange(baseURL + "/api/v0/order/cancel" , HttpMethod.POST, requestEntity, String.class);

        return new Gson().fromJson((String) response.getBody(), OrderCancellationResponse.class);
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