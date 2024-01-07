package com.lirou.store.DTOs.packageDTO;

import java.math.BigDecimal;
import java.util.List;

public record SuperFretePackageDTO(
        Integer id,
        String name,
        BigDecimal price,
        String discount,
        String currency,
        Integer deliveryTime,
        DeliveryRange deliveryRange,
        List<PackageData> packages,
        AdditionalServices additionalServices,
        Company company,
        boolean hasError
) {}
