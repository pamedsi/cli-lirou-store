package com.lirou.store.models.superfrete;

import java.util.List;

import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ProductInfo;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.SuperFreteAddress;

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
