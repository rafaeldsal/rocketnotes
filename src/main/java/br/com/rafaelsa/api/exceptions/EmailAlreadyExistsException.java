package br.com.rafaelsa.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {

  private final HttpStatus status;

  public EmailAlreadyExistsException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
