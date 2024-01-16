package com.lirou.store.DTOs.superfrete;

import java.util.Map;

public record OrderCancellationResponse (
        Map<String, CancelResponse> orderCancellations,
        // Para o caso de erro
        String message,
        String error
) {}

record CancelResponse(boolean canceled) {}
