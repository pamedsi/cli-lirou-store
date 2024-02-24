package com.lirou.store.models.superfrete;

public record AbortingRequest(
    order order
) {}

record order(
        String id,
        String description
){}