package com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete;

import java.util.List;

public record ShippingInfToSendToSuperFreteDTO(
        String platform,
        AddressDTO from,
        AddressDTO to,
        Number service,
        List<Product> products,
        Volumes volumes
){}

record Product (
        String name,
        String quantity,
        String unitary_value
){}

record Volumes (
        Number height,
        Number width,
        Number length,
        Number weight
){}
