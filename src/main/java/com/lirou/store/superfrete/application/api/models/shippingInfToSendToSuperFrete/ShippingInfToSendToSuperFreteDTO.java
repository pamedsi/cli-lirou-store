package com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete;

import com.lirou.store.superfrete.application.api.models.bodyForCalculateShipping.PackageDimensions;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

public record ShippingInfToSendToSuperFreteDTO(
        String platform,
        SuperFreteAddress from,
        SuperFreteAddress to,
        @NumberFormat
        Number service,
        List<ProductInfo> products,
        PackageDimensions volumes
){}
