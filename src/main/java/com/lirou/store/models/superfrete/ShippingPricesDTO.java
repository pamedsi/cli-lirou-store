package com.lirou.store.models.superfrete;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;

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

        public static List<ShippingPricesDTO> severalToDTO (List<SuperFretePackageDTO> responseBody){
                return responseBody.stream().map(ShippingPricesDTO::new).toList();
        }
}