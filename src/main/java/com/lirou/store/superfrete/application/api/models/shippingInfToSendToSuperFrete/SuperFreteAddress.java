package com.lirou.store.superfrete.application.api.models.shippingInfToSendToSuperFrete;

import jakarta.annotation.Nullable;

public record SuperFreteAddress(
        String name,
        String address,
        @Nullable
        String complement,
        @Nullable
        String number,
        String district,
        String city,
        String state_abbr,
        String postal_code,
        @Nullable
        String email
) {}
