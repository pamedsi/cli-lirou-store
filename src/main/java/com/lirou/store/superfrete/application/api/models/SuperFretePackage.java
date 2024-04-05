package com.lirou.store.superfrete.application.api.models;

import java.math.BigDecimal;
import java.util.List;

public record SuperFretePackage(
        Integer id,
        String name,
        BigDecimal price,
        String discount,
        String currency,
        Integer delivery_time,
        DeliveryRange delivery_range,
        List<PackageData> packages,
        AdditionalServices additional_services,
        CompanyInfo company,
        Boolean has_error
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
