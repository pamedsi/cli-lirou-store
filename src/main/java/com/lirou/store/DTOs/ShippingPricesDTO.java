package com.lirou.store.DTOs;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public record ShippingPricesDTO(
        Number id,
        String name,
        Number delivery_time,
        @NumberFormat
        BigDecimal price,
        CompanyInfo company

) {
        public ShippingPricesDTO(SuperFretePackageDTO responseBody) {
                this(
                        responseBody.id(),
                        responseBody.name(),
                        responseBody.deliveryTime(),
                        responseBody.packages().getFirst().price(),
                        responseBody.company()
                );
        }
}