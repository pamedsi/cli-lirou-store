package com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete;

import com.lirou.store.models.superfrete.bodyForCalculateShipping.PackageDimensions;
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
