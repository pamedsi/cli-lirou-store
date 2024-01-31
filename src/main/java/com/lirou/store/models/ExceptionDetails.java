package com.lirou.store.models;

import java.time.LocalDateTime;

public record ExceptionDetails(
        String title,
        int status,
        LocalDateTime timestamp

) {

}
