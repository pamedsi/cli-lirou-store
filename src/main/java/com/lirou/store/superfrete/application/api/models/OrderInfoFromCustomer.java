package com.lirou.store.superfrete.application.api.models;

import java.util.List;

import com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete.ProductInfo;
import com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete.SuperFreteAddress;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderInfoFromCustomer(
		@NotNull
        SuperFreteAddress customerAddress,
        @NotEmpty
        List<ProductInfo> products,
        @NotNull
        Number service
) {}
