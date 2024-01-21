package com.lirou.store.handler;

import com.lirou.store.models.ExceptionDetails;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.ws.rs.BadRequestException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler (BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(BadRequestException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                exception.getMessage(),
                400,
                exception.getClass().getName(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler (ClassNotFoundException.class)
    public ResponseEntity<ExceptionDetails> classNotFoundHandler(ClassNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                exception.getMessage(),
                500,
                exception.getClass().getName(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDetails(
                exception.getMessage(),
                404,
                exception.getClass().getName(),
                LocalDateTime.now()
        ));
    }
}
