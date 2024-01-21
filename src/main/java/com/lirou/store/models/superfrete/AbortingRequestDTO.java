package com.lirou.store.models.superfrete;

public record AbortingRequestDTO(
    order order
) {}

record order(
        String id,
        String description
){}