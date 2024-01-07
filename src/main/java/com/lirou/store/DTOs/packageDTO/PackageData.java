package com.lirou.store.DTOs.packageDTO;

import java.math.BigDecimal;

public record PackageData(
        BigDecimal price,
        BigDecimal discount,
        String format,
        Dimensions dimensions,
        Number weight,
        Number insurance_value
) {}
