package br.com.rafaelsa.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    Map<String, Object> responseBody = new HashMap<>();

    responseBody.put("statusCode", HttpStatus.BAD_REQUEST.value());
    responseBody.put("message", "Erro de validação nos campos");
    responseBody.put("errors", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  @ExceptionHandler(NoteOperationException.class)
  public ResponseEntity<ErrorResponse> handleNoteOperationException(NoteOperationException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus());
    return ResponseEntity.status(ex.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(UserOperationException.class)
  public ResponseEntity<ErrorResponse> handleUserOperationException(UserOperationException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus());
    return ResponseEntity.status(ex.getStatus()).body(errorResponse);
  }
}
