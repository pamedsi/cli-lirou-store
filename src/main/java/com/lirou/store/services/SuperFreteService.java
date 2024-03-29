package com.lirou.store.services;

import static com.lirou.store.validation.PostalCodeValidator.isAValidePostalCode;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lirou.store.exceptions.BadRequestExceptions;
import com.lirou.store.models.superfrete.AbortingRequest;
import com.lirou.store.models.superfrete.DeliveryInfo;
import com.lirou.store.models.superfrete.OrderCancellationResponse;
import com.lirou.store.models.superfrete.OrderInfoFromCustomer;
import com.lirou.store.models.superfrete.OrdersIDs;
import com.lirou.store.models.superfrete.PrintInfo;
import com.lirou.store.models.superfrete.ProtocolData;
import com.lirou.store.models.superfrete.ShippingOfOrder;
import com.lirou.store.models.superfrete.ShippingPrices;
import com.lirou.store.models.superfrete.SuperFretePackage;
import com.lirou.store.models.superfrete.bodyForCalculateShipping.BodyForCalculateShipping;
import com.lirou.store.models.superfrete.bodyForCalculateShipping.PackageDimensions;
import com.lirou.store.models.superfrete.bodyForCalculateShipping.PostalCode;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.SuperFreteAddress;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SuperFreteService {
    private final String baseURL;
    private SuperFreteAddress from;
    private PackageDimensions volumes;
    private HttpHeaders headers;

    public SuperFreteService(@Value("${token}") String token, @Value("${SUPER_FRETE_URL}") String baseURL, @Value("${address}") String address) {
        createFrom(address);
        createVolumes();
        createHeaders(token);
        this.baseURL = baseURL;
    }

    private void createFrom(String address){
        try {
            String[] addressProps = address.split("\\|");
            from = new SuperFreteAddress(
                    addressProps[0],
                    addressProps[1],
                    null,
                    addressProps[2],
                    addressProps[3],
                    addressProps[4],
                    addressProps[5],
                    addressProps[6],
                    addressProps[7]
            );
        }
        catch (Exception ex) {
            System.out.println("Erro ao extrair endereço da variável de ambiente!");
            System.out.println(ex.getMessage());
        }
    }
    private void createVolumes(){
        volumes = new PackageDimensions(20, 11, 16, 0.3);
    }
    private void createHeaders(String token){
        headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Lirou Store " + from.email());
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public List<ShippingPrices> calculateShipping(String to) throws BadRequestExceptions {
        if (!isAValidePostalCode(to)) throw new BadRequestExceptions("CEP inválido!");

        RestTemplate restTemplate = new RestTemplate();
        String services =  "1,2,17";
        BodyForCalculateShipping body = new BodyForCalculateShipping(
                new PostalCode(from.postal_code()),
                new PostalCode(removeNonDigits(to)),
                services, volumes
        );
        String jsonBody = new Gson().toJson(body).replace("_dimensions", "");
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonBody, headers);
        log.info("Calculando frete...");
        String jsonResponseBody = restTemplate.exchange(baseURL + "/api/v0/calculator", HttpMethod.POST, requestEntity, String.class).getBody();
        log.info("Frete calculado.");
        Type pricesList = new TypeToken<List<SuperFretePackage>>(){}.getType();
        List<SuperFretePackage> responseBody = new Gson().fromJson(jsonResponseBody, pricesList);
        assert responseBody != null;
        return ShippingPrices.severalToDTO(responseBody);
    }

    public ProtocolData createShippingOrder(OrderInfoFromCustomer orderInfo){
        ShippingInfToSendToSuperFreteDTO body = new ShippingInfToSendToSuperFreteDTO(
                "Lirou Store",
                from,
                orderInfo.customerAddress(),
                orderInfo.service(),
                orderInfo.products(),
                volumes
        );
        String json = new Gson().toJson(body, ShippingInfToSendToSuperFreteDTO.class);
        HttpEntity<?> requestEntityToSuperFrete = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        log.info("Gerando etiqueta...");
        String responseBodyJson = restTemplate.exchange(baseURL + "/api/v0/cart" , HttpMethod.POST, requestEntityToSuperFrete, String.class).getBody();
        log.info("Etiqueta gerada.");
        return new Gson().fromJson(responseBodyJson, ProtocolData.class);
    }
    // Pagando a etiqueta e gerando o PDF (confirmar pedido):
    public ShippingOfOrder payShipping(ProtocolData response) {
        // Persistir a response
        // ...
        String ordersIDs = new Gson().toJson(new OrdersIDs(List.of(response.id())));
        HttpEntity<?> requestEntityToPayShipping = new HttpEntity<>(ordersIDs, headers);
        RestTemplate restTemplate = new RestTemplate();
        log.info("Pagando frete...");
        String responseBody = restTemplate.exchange(baseURL + "/api/v0/checkout" , HttpMethod.POST, requestEntityToPayShipping, String.class).getBody();
        log.info("Frete pago.");
        return new Gson().fromJson(responseBody, ShippingOfOrder.class);
    }

    public DeliveryInfo getDeliveryInfo(String orderID){
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String responseBody = restTemplate.exchange(baseURL + "/api/v0/order/info/" + orderID , HttpMethod.GET, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, DeliveryInfo.class);
    }
    public PrintInfo getPrintableLabel(OrdersIDs ordersIDs){
        HttpEntity<?> requestEntity = new HttpEntity<>(ordersIDs, headers);
        RestTemplate restTemplate = new RestTemplate();
        String responseBody = restTemplate.exchange(baseURL + "/api/v0/tag/print" , HttpMethod.POST, requestEntity, String.class).getBody();
        return new Gson().fromJson(responseBody, PrintInfo.class);
    }
    public ShippingOfOrder getPrintableLabel(OrderInfoFromCustomer orderInfo){
        ProtocolData protocolData = createShippingOrder(orderInfo);
        return payShipping(protocolData);
    }
    public OrderCancellationResponse cancelOrder(AbortingRequest requestBody){
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.exchange(baseURL + "/api/v0/order/cancel" , HttpMethod.POST, requestEntity, String.class);
        return new Gson().fromJson((String) response.getBody(), OrderCancellationResponse.class);
    }

    public static String removeNonDigits(String cep) {
        return cep.replaceAll("[^0-9]", "");
    }



}