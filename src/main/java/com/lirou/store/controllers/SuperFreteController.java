package com.lirou.store.controllers;

import com.lirou.store.DTOs.superfrete.*;
import com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.models.Message;
import com.lirou.store.services.SuperFreteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lirou.store.validation.PostalCodeValidator.isAValidePostalCode;

@RestController
@RequestMapping("/api/shipping")
public class SuperFreteController {

    private final SuperFreteService superFreteService;

    public SuperFreteController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate/{CEP}")
    public ResponseEntity<?> calculateShipping(@PathVariable("CEP") String postalCode) {
        if (!isAValidePostalCode(postalCode)) return ResponseEntity.badRequest().body((new Message("CEP inválido!")));
        List<ShippingPricesDTO> body = superFreteService.calculateShipping(postalCode);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/send-to-superfrete")
    public ResponseEntity<?> sendShippingToSuperFrete(@RequestBody ShippingInfToSendToSuperFreteDTO body) {
        ProtocolData response = superFreteService.sendShippingToSuperFrete(body);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/confirm")
    public ResponseEntity<?> checkout(@RequestBody OrdersIDs body) {
        ShippingOfOrderDTO response = superFreteService.finishOrderAndGeneratePrintableLabel(body);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{orderID}")
    public ResponseEntity<?> getOrderInfo(@PathVariable("orderID") String orderID) {
        DeliveryInfoDTO response = superFreteService.getDeliveryInfo(orderID);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/printable-label")
    public ResponseEntity<?> getPrintableLabel(@RequestBody OrdersIDs orders) {
        PrintInfo response = superFreteService.getPrintableLabel(orders);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/abort-order")
    public ResponseEntity<?> cancelOrder(@RequestBody AbortingRequestDTO body) {
        OrderCancellationResponse response = superFreteService.cancelOrder(body);
        return ResponseEntity.ok(response);
    }
}
