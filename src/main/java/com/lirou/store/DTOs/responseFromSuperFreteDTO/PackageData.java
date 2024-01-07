package com.lirou.store.DTOs.responseFromSuperFreteDTO;

import java.math.BigDecimal;

public record PackageData(
        BigDecimal price,
        BigDecimal discount,
        String format,
        Dimensions dimensions,
        Number weight,
        Number insurance_value
) {}
