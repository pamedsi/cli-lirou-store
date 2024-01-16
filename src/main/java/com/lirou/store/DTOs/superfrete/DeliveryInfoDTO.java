package com.lirou.store.DTOs.superfrete;

import com.lirou.store.DTOs.superfrete.shippingInfToSendToSuperFrete.AddressDTO;
import jdk.jfr.Timestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record DeliveryInfoDTO(
        String id,
        String protocol,
        String format,
        Number delivery,
        Number delivery_min,
        Number delivery_max,
        Number discount,
        String height,
        String width,
        String length,
        String weight,
        AddressDTO from,
        AddressDTO to,
        Object invoice,
        Boolean own_hand,
        Boolean receipt,
        Number price,
        String tracking,
        String status,
        Number service_id,
        List<Object> products,
        String insurance_value,
        @DateTimeFormat
        String generated_at,
        @DateTimeFormat
        String created_at,
        @DateTimeFormat
        String updated_at,
        PrintInfo print,
        List<String> tags
) {}