package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.config.TokenService;
import br.com.rafaelsa.api.dtos.authentication.AuthenticationRequestDTO;
import br.com.rafaelsa.api.dtos.authentication.AuthenticationResponseDTO;
import br.com.rafaelsa.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequestDTO.email(), authenticationRequestDTO.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var user = (User) auth.getPrincipal();
    var token = tokenService.generateToken(user);

    long expiresIn = tokenService.getExpirationTime();

    return new AuthenticationResponseDTO(token, expiresIn);
  }
}
