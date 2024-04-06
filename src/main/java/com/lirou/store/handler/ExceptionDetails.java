package com.lirou.store.handler;

import java.time.LocalDateTime;

public record ExceptionDetails(
        String title,
        String message,
        int status,
        LocalDateTime timestamp,
        String path,
        String method
) {}
