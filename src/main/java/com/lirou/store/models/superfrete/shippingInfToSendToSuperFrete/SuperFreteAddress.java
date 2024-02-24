package com.lirou.store.models.superfrete.shippingInfToSendToSuperFrete;

import java.util.Optional;

public record SuperFreteAddress(
        String name,
        String address,
        Optional<String> complement,
        Optional<String> number,
        String district,
        String city,
        String state_abbr,
        String postal_code,
        Optional<String> email
) {}
