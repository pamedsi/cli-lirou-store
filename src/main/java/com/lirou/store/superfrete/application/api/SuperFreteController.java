package com.lirou.store.superfrete.application.api;

import java.util.List;

import com.lirou.store.handler.exceptions.BadRequestExceptions;
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
import com.lirou.store.superfrete.application.service.SuperFreteApplicationService;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class SuperFreteController implements SuperFreteAPI {
    private final SuperFreteApplicationService superFreteApplicationService;

    @Override
    public ResponseEntity<List<ShippingPrices>> calculateShipping(String postalCode) throws BadRequestExceptions {
        log.info("[Inicia] SuperFreteService - calculateShipping()");
        List<ShippingPrices> body = superFreteApplicationService.calculateShipping(postalCode);
        log.info("[Finaliza] SuperFreteService - calculateShipping()");
        return ResponseEntity.ok(body);
    }
    @Override
    public ResponseEntity<ShippingOfOrder> sendShippingToSuperFrete(OrderInfoFromCustomer orderInfo) {
        log.info("[Inicia] SuperFreteService - getPrintableLabel()");
        ShippingOfOrder response = superFreteApplicationService.getPrintableLabel(orderInfo);
        log.info("[Finaliza] SuperFreteService - getPrintableLabel()");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<DeliveryInfo> getOrderInfo(String orderID) {
        log.info("[Inicia] SuperFreteService - getDeliveryInfo()");
        DeliveryInfo response = superFreteApplicationService.getDeliveryInfo(orderID);
        log.info("[Finaliza] SuperFreteService - getDeliveryInfo()");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<PrintInfo> getPrintableLabel(OrdersIDs orders) {
        log.info("[Inicia] SuperFreteService - getPrintableLabel()");
        PrintInfo response = superFreteApplicationService.getPrintableLabel(orders);
        log.info("[Finaliza] SuperFreteService - getPrintableLabel()");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<OrderCancellationResponse> cancelOrder( AbortingRequest body) {
        log.info("[Inicia] SuperFreteService - cancelOrder()");
        OrderCancellationResponse response = superFreteApplicationService.cancelOrder(body);
        log.info("[Finaliza] SuperFreteService - cancelOrder()");
        return ResponseEntity.ok(response);
    }
}
