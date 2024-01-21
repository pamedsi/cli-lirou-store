package com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete;

import java.util.List;

public record ShippingInfToSendToSuperFreteDTO(
        String platform,
        AddressDTO from,
        AddressDTO to,
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
