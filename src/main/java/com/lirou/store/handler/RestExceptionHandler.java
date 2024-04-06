package com.lirou.store.handler;

import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.handler.exceptions.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.ws.rs.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestHandler(BadRequestException exception,  HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                getExceptionTitle(exception.getClass().toString()),
                exception.getMessage(),
                400,
                LocalDateTime.now(),
                request.getServletPath(),
                request.getMethod()
        ));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionDetails> notFoundHandler(NotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDetails(
                        getExceptionTitle(exception.getClass().toString()),
                        exception.getMessage(),
                        404,
                        LocalDateTime.now(),
                        request.getServletPath(),
                        request.getMethod()
                ));
    }

    @ExceptionHandler (UnauthorizedException.class)
    public ResponseEntity<ExceptionDetails> unauthorisedHandler(UnauthorizedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body (
                new ExceptionDetails (
                        getExceptionTitle(exception.getClass().toString()),
                        exception.getMessage(),
                        401,
                        LocalDateTime.now(),
                        request.getServletPath(),
                        request.getMethod()
                ));
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<?> generalException(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDetails(
                        getExceptionTitle(exception.getClass().toString()),
                        exception.getMessage(),
                        500,
                        LocalDateTime.now(),
                        request.getServletPath(),
                        request.getMethod()
                ));
    }
    
    private String getExceptionTitle(String classTitle) {
        return List.of(classTitle.split("\\.")).getLast().replace("Exception", "");
    }
}