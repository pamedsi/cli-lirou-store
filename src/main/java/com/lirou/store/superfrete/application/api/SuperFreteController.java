package com.lirou.store.superfrete.application.api;

import java.util.List;

import com.lirou.store.handler.exceptions.BadRequestExceptions;
import com.lirou.store.superfrete.application.service.SuperFreteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import com.lirou.store.superfrete.application.api.models.AbortingRequest;
import com.lirou.store.superfrete.application.api.models.DeliveryInfo;
import com.lirou.store.superfrete.application.api.models.OrderCancellationResponse;
import com.lirou.store.superfrete.application.api.models.OrderInfoFromCustomer;
import com.lirou.store.superfrete.application.api.models.OrdersIDs;
import com.lirou.store.superfrete.application.api.models.PrintInfo;
import com.lirou.store.superfrete.application.api.models.ShippingOfOrder;
import com.lirou.store.superfrete.application.api.models.ShippingPrices;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class SuperFreteController implements SuperFreteAPI {
    private final SuperFreteService superFreteService;

    @Override
    public ResponseEntity<List<ShippingPrices>> calculateShipping(String postalCode) throws BadRequestExceptions {
        log.info("[starts] SuperFreteController - calculateShipping()");
        List<ShippingPrices> body = superFreteService.calculateShipping(postalCode);
        log.info("[ends] SuperFreteController - calculateShipping()");
        return ResponseEntity.ok(body);
    }
    @Override
    public ResponseEntity<ShippingOfOrder> sendShippingToSuperFrete(OrderInfoFromCustomer orderInfo) {
        log.info("[starts] SuperFreteController - getPrintableLabel()");
        ShippingOfOrder response = superFreteService.getPrintableLabel(orderInfo);
        log.info("[ends] SuperFreteController - getPrintableLabel()");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<DeliveryInfo> getOrderInfo(String orderID) {
        log.info("[starts] SuperFreteServiceSuperFreteService - getDeliveryInfo()");
        DeliveryInfo response = superFreteService.getDeliveryInfo(orderID);
        log.info("[ends] SuperFreteController - getDeliveryInfo()");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<PrintInfo> getPrintableLabel(OrdersIDs orders) {
        log.info("[starts] SuperFreteController - getPrintableLabel()");
        log.info("[starts] SuperFreteController - getPrintableLabel()");
        PrintInfo response = superFreteService.getPrintableLabel(orders);
        log.info("[ends] SuperFreteController - getPrintableLabel()");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<OrderCancellationResponse> cancelOrder( AbortingRequest body) {
        log.info("[starts] SuperFreteController - cancelOrder()");
        OrderCancellationResponse response = superFreteService.cancelOrder(body);
        log.info("[ends] SuperFreteController - cancelOrder()");
        return ResponseEntity.ok(response);
    }
}
