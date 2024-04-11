package com.lirou.store.handler;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionDetails(
        String title,
        String message,
        int status,
        LocalDateTime timestamp,
        String path,
        String method
) {
    public static String getMessageFromDataIntegrityViolation(String message) {
        return message.split("Detail: ")[1].split("]")[0];
    }

    public static String getExceptionTitle(String classTitle) {
        return List.of(classTitle.split("\\.")).getLast().replace("Exception", "");
    }
}