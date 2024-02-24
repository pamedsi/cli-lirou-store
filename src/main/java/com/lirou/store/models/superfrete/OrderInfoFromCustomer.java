package com.lirou.store.models.superfrete;

import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.ProductInfo;
import com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete.SuperFreteAddress;

import java.util.List;

public record OrderInfoFromCustomer(
        SuperFreteAddress customerAddress,
        List<ProductInfo> products,
        Number service
) {}
