package com.lirou.store.models.superfrete;

import org.springframework.format.annotation.NumberFormat;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public record ProtocolData(
        String id,
        @NumberFormat
        BigDecimal price,
        String protocol,
        @SerializedName("self_tracking")
        String selfTracking,
        String status
) {}
