package com.productivity.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExpiredJwtException.class})
    protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        String message = "JWT token has expired";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}

