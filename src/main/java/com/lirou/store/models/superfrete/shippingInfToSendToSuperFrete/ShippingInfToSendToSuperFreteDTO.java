package com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete;

import org.springframework.format.annotation.NumberFormat;

import java.util.List;

public record ShippingInfToSendToSuperFreteDTO(
        String platform,
        SuperFreteAddress from,
        SuperFreteAddress to,
        @NumberFormat
        Number service,
        List<ProductInfo> products,
        Volumes volumes
){}

record Volumes (
        Number height,
        Number width,
        Number length,
        Number weight
){}
