package com.lirou.store.models;

import java.time.LocalDateTime;

public record ExceptionDetails(
    String title,
    int status,
    String developerMessage,
    LocalDateTime timestamp
) {
}
