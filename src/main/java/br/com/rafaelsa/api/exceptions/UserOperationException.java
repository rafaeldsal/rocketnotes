package br.com.rafaelsa.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UserOperationException extends RuntimeException{

  private final HttpStatus status;

  public UserOperationException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
