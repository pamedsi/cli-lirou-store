package com.lirou.store.DTOs;

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
        CompanyInfo company,
        Boolean hasError
) {}

record AdditionalServices(
        Boolean receipt,
        Boolean own_hand
) {}

record CompanyInfo(
        Number id,
        String name,
        String picture,
        Boolean has_error
) {}

record DeliveryRange(
        Integer min,
        Integer max
) {}

record Dimensions(
        Number height,
        Number width,
        Number length
) {}

record PackageData(
        BigDecimal price,
        BigDecimal discount,
        String format,
        Dimensions dimensions,
        Number weight,
        Number insurance_value
) {}
