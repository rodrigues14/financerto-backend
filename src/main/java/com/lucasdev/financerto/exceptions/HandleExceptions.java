package com.lucasdev.financerto.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleExceptions {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleErrorNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleErrorBadRequest(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(FieldsErrorsDTO::new).toList());
    }

    private record FieldsErrorsDTO(String field, String messege) {

        public FieldsErrorsDTO(FieldError e) {
            this(e.getField(), e.getDefaultMessage());
        }
    }

}
