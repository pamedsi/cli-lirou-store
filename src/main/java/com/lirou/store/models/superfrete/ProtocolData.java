package com.lirou.store.models.superfrete;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public record ProtocolData(
        String id,
        @NumberFormat
        BigDecimal price,
        String protocol,
        String selfTracking,
        String status
) {}
