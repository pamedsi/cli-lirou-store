package com.lirou.store.models.superfrete;

import java.util.Map;

public record OrderCancellationResponse (
        Map<String, CancelResponse> orderCancellations,
        // Para o caso de erro
        String message,
        String error
) {}

