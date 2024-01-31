package com.lirou.store.handler;

import com.lirou.store.models.ExceptionDetails;

import jakarta.ws.rs.NotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.method.ParameterValidationResult;

import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.ws.rs.BadRequestException;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(BadRequestException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                exception.getMessage(),
                400,
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ExceptionDetails> classNotFoundHandler(ClassNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                exception.getMessage(),
                500,
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDetails(
                        exception.getMessage(),
                        404,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(HandlerMethodValidationException exception) {
        List<ParameterValidationResult> validationResults = exception.getAllValidationResults();
        List<List<String>> errors = validationResults.stream().map(error -> error.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList()).toList();

        ExceptionDetails body = new ExceptionDetails(
                errors.toString(),
                400,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler (DataIntegrityViolationException.class)
    public ResponseEntity<?> repeatedDataHandler(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionDetails(
                        ex.getMessage(),
                        409,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler (HttpMessageNotReadableException.class)
    public ResponseEntity<?> noBodyHandler(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDetails(
                        getOnlyMessageValue(ex),
                        400,
                        LocalDateTime.now()
                ));
    }

    private String getOnlyMessageValue(Exception ex) {
        return ex.getMessage().split(":")[0];
    }
}