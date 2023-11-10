package com.demo.learning.application.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", HttpStatus.NOT_FOUND);
    errorMap.put("error", exception.getErrorCode());
    errorMap.put("message", exception.getMessage());
    return ResponseEntity.badRequest().body(errorMap);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> handleValidationFieldException(BadRequestException exception) {
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("status", HttpStatus.BAD_REQUEST);
    errorMap.put("error", exception.getErrorCode());
    errorMap.put("message", exception.getMessage());
    return ResponseEntity.badRequest().body(errorMap);
  }
}
