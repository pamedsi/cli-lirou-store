package com.lirou.store.models.superfrete;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;

public record ShippingPrices(
        Number id,
        String name,
        Number delivery_time,
        @NumberFormat
        BigDecimal price,
        CompanyInfo company

) {
        public ShippingPrices(SuperFretePackage responseBody) {
                this(
                        responseBody.id(),
                        responseBody.name(),
                        responseBody.delivery_time(),
                        responseBody.packages().getFirst().price(),
                        responseBody.company()
                );
        }

        public static List<ShippingPrices> severalToDTO (List<SuperFretePackage> responseBody){
                return responseBody.stream().map(ShippingPrices::new).toList();
        }
}