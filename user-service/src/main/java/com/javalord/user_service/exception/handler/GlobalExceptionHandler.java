package com.javalord.user_service.exception.handler;

import com.javalord.user_service.common.RestResponse;
import com.javalord.user_service.common.Status;
import com.javalord.user_service.common.ValidationError;
import com.javalord.user_service.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ValidationError> errors = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        RestResponse<List<ValidationError>> response = new RestResponse<>(
                Status.ERROR,
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {

        RestResponse<String> response = new RestResponse<>(
                Status.ERROR,
                "Validation failed",
                ex.getLocalizedMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
