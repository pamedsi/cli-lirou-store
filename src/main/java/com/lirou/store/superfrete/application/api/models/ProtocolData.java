package com.lirou.store.superfrete.application.api.models;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public record ProtocolData(
        String id,
        @NumberFormat
        BigDecimal price,
        String protocol,
        String self_tracking,
        String status
) {}
