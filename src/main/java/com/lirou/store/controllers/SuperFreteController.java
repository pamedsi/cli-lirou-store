package com.lirou.store.controllers;

import java.util.List;

import com.lirou.store.exceptions.BadRequestExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lirou.store.exceptions.BadRequestExceptions;
import com.lirou.store.models.superfrete.AbortingRequest;
import com.lirou.store.models.superfrete.DeliveryInfo;
import com.lirou.store.models.superfrete.OrderCancellationResponse;
import com.lirou.store.models.superfrete.OrderInfoFromCustomer;
import com.lirou.store.models.superfrete.OrdersIDs;
import com.lirou.store.models.superfrete.PrintInfo;
import com.lirou.store.models.superfrete.ShippingOfOrder;
import com.lirou.store.models.superfrete.ShippingPrices;
import com.lirou.store.services.SuperFreteService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/shipping")
@Log4j2
public class SuperFreteController {

    private final SuperFreteService superFreteService;

    public SuperFreteController(SuperFreteService superFreteService) {
        this.superFreteService = superFreteService;
    }

    @GetMapping("/calculate/{CEP}")
    public ResponseEntity<?> calculateShipping(@PathVariable("CEP") String postalCode) throws BadRequestExceptions {
        log.info("[Inicia] SuperFreteService - calculateShipping()");
        List<ShippingPrices> body = superFreteService.calculateShipping(postalCode);
        log.info("[Finaliza] SuperFreteService - calculateShipping()");
        return ResponseEntity.ok(body);
    }
    @PostMapping
    public ResponseEntity<ShippingOfOrder> sendShippingToSuperFrete(@RequestBody @Valid OrderInfoFromCustomer orderInfo) {
        log.info("[Inicia] SuperFreteService - getPrintableLabel()");
        ShippingOfOrder response = superFreteService.getPrintableLabel(orderInfo);
        log.info("[Finaliza] SuperFreteService - getPrintableLabel()");
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
