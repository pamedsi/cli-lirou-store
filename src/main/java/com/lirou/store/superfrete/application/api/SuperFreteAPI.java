package com.lirou.store.superfrete.application.api;

import com.lirou.store.handler.exceptions.BadRequestExceptions;
import com.lirou.store.superfrete.application.api.models.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/shipping")
public interface SuperFreteAPI {
    @GetMapping("/calculate/{CEP}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> calculateShipping(@PathVariable("CEP") String postalCode) throws BadRequestExceptions;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ShippingOfOrder> sendShippingToSuperFrete(@RequestBody @Valid OrderInfoFromCustomer orderInfo);

    @GetMapping("/info/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<DeliveryInfo> getOrderInfo(@PathVariable("orderID") String orderID);

    @GetMapping("/printable-label")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PrintInfo> getPrintableLabel(@RequestBody OrdersIDs orders);

    @DeleteMapping("/abort-order")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<OrderCancellationResponse> cancelOrder(@RequestBody AbortingRequest body);
}
