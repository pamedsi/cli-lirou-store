package com.lirou.store.controllers;

import com.lirou.store.models.superfrete.*;
import com.lirou.store.services.SuperFreteService;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
@Log4j2
public class SuperFreteController {

    private final SuperFreteService superFreteService;

    public SuperFreteController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate/{CEP}")
    public ResponseEntity<?> calculateShipping(@PathVariable("CEP") String postalCode) {
        log.info("[Inicia] SuperFreteService - calculateShipping()");
        List<ShippingPrices> body = superFreteService.calculateShipping(postalCode);
        log.info("[Finaliza] SuperFreteService - calculateShipping()");
        return ResponseEntity.ok(body);
    }
    @PostMapping("/pay")
    public ResponseEntity<ShippingOfOrderDTO> sendShippingToSuperFrete(@RequestBody OrderInfoFromCustomer orderInfo) {
        log.info("[Inicia] SuperFreteService - sendShippingToSuperFrete()");
        ShippingOfOrderDTO response = superFreteService.generatePrintableLabel(orderInfo);
        log.info("[Finaliza] SuperFreteService - sendShippingToSuperFrete()");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{orderID}")
    public ResponseEntity<DeliveryInfo> getOrderInfo(@PathVariable("orderID") String orderID) {
        log.info("[Inicia] SuperFreteService - getDeliveryInfo()");
        DeliveryInfo response = superFreteService.getDeliveryInfo(orderID);
        log.info("[Finaliza] SuperFreteService - getDeliveryInfo()");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/printable-label")
    public ResponseEntity<PrintInfo> getPrintableLabel(@RequestBody OrdersIDs orders) {
        log.info("[Inicia] SuperFreteService - getPrintableLabel()");
        PrintInfo response = superFreteService.getPrintableLabel(orders);
        log.info("[Finaliza] SuperFreteService - getPrintableLabel()");
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/abort-order")
    public ResponseEntity<OrderCancellationResponse> cancelOrder(@RequestBody AbortingRequest body) {
        log.info("[Inicia] SuperFreteService - cancelOrder()");
        OrderCancellationResponse response = superFreteService.cancelOrder(body);
        log.info("[Finaliza] SuperFreteService - cancelOrder()");
        return ResponseEntity.ok(response);
    }
}
