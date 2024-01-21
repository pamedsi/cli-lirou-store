package com.lirou.store.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lirou.store.models.ExceptionDetails;

import jakarta.ws.rs.NotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.method.ParameterValidationResult;

import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.ws.rs.BadRequestException;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(BadRequestException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                exception.getMessage(),
                400,
                exception.getClass().getName(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ExceptionDetails> classNotFoundHandler(ClassNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                exception.getMessage(),
                500,
                exception.getClass().getName(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDetails(
                        exception.getMessage(),
                        404,
                        exception.getClass().getName(),
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
                exception.getClass().getName(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler (HttpMessageConversionException.class)
    public ResponseEntity<?> testando(HttpMessageConversionException erro){
        return ResponseEntity.ok("oi");
    }
}