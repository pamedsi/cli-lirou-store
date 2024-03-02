package com.lirou.store.models.superfrete;

public record ShippingOfOrder(
        Boolean success,
        Purchase purchase
) {}

record Purchase(
        String id,
        Number price,
        Number discount,
        int service_id,
        String tracking,
        PrintInfo print
) {}