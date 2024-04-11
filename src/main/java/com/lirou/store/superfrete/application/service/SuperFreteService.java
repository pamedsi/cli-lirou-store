package com.lirou.store.superfrete.application.service;

import com.lirou.store.handler.exceptions.BadRequestExceptions;
import com.lirou.store.superfrete.application.api.models.*;

import java.util.List;

public interface SuperFreteService {
    List<ShippingPrices> calculateShipping(String to) throws BadRequestExceptions;
    ProtocolData createShippingOrder(OrderInfoFromCustomer orderInfo);
    ShippingOfOrder payShipping(ProtocolData response);
    DeliveryInfo getDeliveryInfo(String orderID);
    PrintInfo getPrintableLabel(OrdersIDs ordersIDs);
    ShippingOfOrder getPrintableLabel(OrderInfoFromCustomer orderInfo);
    OrderCancellationResponse cancelOrder(AbortingRequest requestBody);
}
