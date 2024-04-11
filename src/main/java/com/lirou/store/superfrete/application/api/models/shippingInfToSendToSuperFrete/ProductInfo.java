package com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete;

import org.springframework.format.annotation.NumberFormat;

public record ProductInfo(
        String name,
        @NumberFormat
        String quantity,
        @NumberFormat
        String unitary_value
){}