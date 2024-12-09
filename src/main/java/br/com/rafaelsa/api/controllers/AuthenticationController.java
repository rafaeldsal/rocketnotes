package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.users.requests.AuthenticationUserRequestDTO;
import br.com.rafaelsa.api.dtos.users.requests.UserRequestDTO;
import br.com.rafaelsa.api.dtos.users.response.AuthenticationUserResponseDTO;
import br.com.rafaelsa.api.entities.User;
import br.com.rafaelsa.api.servicies.TokenService;
import br.com.rafaelsa.api.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody AuthenticationUserRequestDTO authenticationUserDTO) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationUserDTO.email(), authenticationUserDTO.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.ok(new AuthenticationUserResponseDTO(token));
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody UserRequestDTO userRequestDTO) {
    return userService.create(userRequestDTO);
  }
}
