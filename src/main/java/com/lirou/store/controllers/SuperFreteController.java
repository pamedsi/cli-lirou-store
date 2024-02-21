package com.lirou.store.controllers;

import com.lirou.store.models.superfrete.*;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ShippingInfToSendToSuperFreteDTO;
import com.lirou.store.services.SuperFreteService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class SuperFreteController {

    private final SuperFreteService superFreteService;

    public SuperFreteController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate/{CEP}")
    public ResponseEntity<?> calculateShipping(@PathVariable("CEP") String postalCode) {
        List<ShippingPricesDTO> body = superFreteService.calculateShipping(postalCode);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/send-to-superfrete")
    public ResponseEntity<ProtocolData> sendShippingToSuperFrete(@RequestBody @Valid ShippingInfToSendToSuperFreteDTO body) {
        ProtocolData response = superFreteService.sendShippingToSuperFrete(body);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/confirm")
    public ResponseEntity<ShippingOfOrderDTO> checkout(@RequestBody OrdersIDs body) {
        ShippingOfOrderDTO response = superFreteService.finishOrderAndGeneratePrintableLabel(body);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{orderID}")
    public ResponseEntity<DeliveryInfoDTO> getOrderInfo(@PathVariable("orderID") String orderID) {
        DeliveryInfoDTO response = superFreteService.getDeliveryInfo(orderID);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/printable-label")
    public ResponseEntity<PrintInfo> getPrintableLabel(@RequestBody OrdersIDs orders) {
        PrintInfo response = superFreteService.getPrintableLabel(orders);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/abort-order")
    public ResponseEntity<OrderCancellationResponse> cancelOrder(@RequestBody AbortingRequestDTO body) {
        OrderCancellationResponse response = superFreteService.cancelOrder(body);
        return ResponseEntity.ok(response);
    }
}
