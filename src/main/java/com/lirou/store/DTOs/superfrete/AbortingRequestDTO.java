package com.lirou.store.DTOs.superfrete;

public record AbortingRequestDTO(
    order order
) {}

record order(
        String id,
        String description
){}