package com.lirou.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lirou.store.DTOs.superfrete.*;
import com.lirou.store.DTOs.superfrete.bodyForCalculateShipping.CEPToSendToDTO;
import com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.services.SuperFreteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private final SuperFreteService superFreteService;

    public ShippingController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> calculateShipping(@RequestBody CEPToSendToDTO postalCodes) throws IOException {
        List<ShippingPricesDTO> body = superFreteService.calculateShipping(postalCodes.CEP());
        return ResponseEntity.ok(body);
    }
    @PostMapping("/send-to-superfrete")
    public ResponseEntity<?> sendShippingToSuperFrete(@RequestBody ShippingInfToSendToSuperFreteDTO body) throws JsonProcessingException {
        ProtocolData response = superFreteService.sendShippingToSuperFrete(body);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> checkout(@RequestBody OrdersIDs body) {
        ShippingOfOrderDTO response = superFreteService.finishOrderAndGenerateTag(body);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{orderID}")
    public ResponseEntity<?> getOrderInfo(@PathVariable("orderID") String orderID) {
        DeliveryInfoDTO response = superFreteService.getDeliveryInfo(orderID);
//        String response = superFreteService.getDeliveryInfo(orderID);
        return ResponseEntity.ok(response);
    }
}
