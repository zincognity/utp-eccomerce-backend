package com.utepinos.utp_ecommerce.api.shared.adapter.in.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.utepinos.utp_ecommerce.api.user.domain.exception.EmailAlreadyInUseException;

/**
 * * Global Exception Handler para las excepciones.
 * ! Se puede ir mejorando el manejo de excepciones.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmailAlreadyInUseException.class)
  public ResponseEntity<Map<String, String>> handleUserAlreadyExists(EmailAlreadyInUseException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
  }
}
