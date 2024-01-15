package com.lirou.store.DTOs.superfrete;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;

public record ShippingOfOrderDTO(
        Boolean success,
        Purchase purchase
) {}

record Order(
        String id,
        String protocol,
        Number service_id,
        @NumberFormat
        BigDecimal price,
        @NumberFormat
        BigDecimal discount,
        String self_tracking,
        String tracking,
        Print print
) {}

record Purchase(
        String id,
        String status,
        List<Order> orders
) {}

record Print(String url) {}