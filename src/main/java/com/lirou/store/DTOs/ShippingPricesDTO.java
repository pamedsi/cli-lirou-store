package com.lirou.store.DTOs;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public record ShippingPricesDTO(
        String id,
        String name,
        Number delivery_time,
        @NumberFormat
        BigDecimal price,
        CompanyInfo company

) {}

record CompanyInfo (
        Number id,
        String name,
        String picture
){}
