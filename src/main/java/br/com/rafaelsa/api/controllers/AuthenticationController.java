package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.authentication.AuthenticationRequestDTO;
import br.com.rafaelsa.api.servicies.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/auth")
  public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO) {
    try {
      var token = authenticationService.authenticate(authenticationRequestDTO);
      return ResponseEntity.ok().body(token);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
