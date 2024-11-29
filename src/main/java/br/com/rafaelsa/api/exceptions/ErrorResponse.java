package br.com.rafaelsa.api.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}
