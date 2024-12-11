package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.users.requests.RegisterUserDTO;
import br.com.rafaelsa.api.dtos.users.response.LoginResponse;
import br.com.rafaelsa.api.dtos.users.response.LoginUserDTO;
import br.com.rafaelsa.api.dtos.users.response.UserResponseDTO;
import br.com.rafaelsa.api.entities.User;
import br.com.rafaelsa.api.servicies.AuthenticationService;
import br.com.rafaelsa.api.servicies.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterUserDTO registerUserDTO) {
    return authenticationService.register(registerUserDTO);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
    User authenticateUser = authenticationService.authenticate(loginUserDTO);

    String jwtToken = jwtService.generateToken(authenticateUser);

    System.out.println(authenticateUser.toString());

    LoginResponse loginResponse = new LoginResponse(authenticateUser, jwtToken, jwtService.getExpirationTime());

    return ResponseEntity.ok(loginResponse);
  }


}
