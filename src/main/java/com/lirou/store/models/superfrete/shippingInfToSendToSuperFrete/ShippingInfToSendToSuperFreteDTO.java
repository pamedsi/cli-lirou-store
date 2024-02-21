package com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ShippingInfToSendToSuperFreteDTO(
        @NotBlank
	String platform,
	@NotNull
        AddressDTO from,
        @NotNull
        AddressDTO to,
        @NotNull
        Number service,
        @NotEmpty
        List<ProductInfo> products,
        Volumes volumes
){}

record Volumes (
        Number height,
        Number width,
        Number length,
        Number weight
){}
