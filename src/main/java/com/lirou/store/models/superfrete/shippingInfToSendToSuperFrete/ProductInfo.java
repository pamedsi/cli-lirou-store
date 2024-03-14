package com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete;

import org.springframework.format.annotation.NumberFormat;

public record ProductInfo(
        String name,
        @NumberFormat
        String quantity,
        @NumberFormat
        String unitary_value
){}