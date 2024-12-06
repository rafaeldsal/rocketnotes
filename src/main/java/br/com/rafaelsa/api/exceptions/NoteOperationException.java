package br.com.rafaelsa.api.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoteOperationException extends RuntimeException{
  private final HttpStatus status;

  public NoteOperationException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
