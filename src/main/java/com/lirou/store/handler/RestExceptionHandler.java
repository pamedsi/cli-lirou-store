package com.lirou.store.handler;

import com.lirou.store.handler.exceptions.NotFoundException;
import com.lirou.store.handler.exceptions.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.ws.rs.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
@Log4j2
public class RestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, BadRequestException.class})
    public ResponseEntity<ExceptionDetails> badRequestHandler(Exception exception,  HttpServletRequest request) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionDetails(
                getExceptionTitle(exception.getClass().toString()),
                "Verifique se os campos estão preenchidos corretamente!",
                400,
                LocalDateTime.now(),
                request.getServletPath(),
                request.getMethod()
        ));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> dataIntegrityViolationHandler(DataIntegrityViolationException exception,  HttpServletRequest request) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDetails(
                getExceptionTitle(exception.getClass().toString()),
                "Um dos campos que você inseriu já existe no banco de dados.",
                409,
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
                        "Erro interno! Favor contatar o suporte!",
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