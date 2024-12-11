package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.dtos.users.requests.RegisterUserDTO;
import br.com.rafaelsa.api.dtos.users.response.LoginUserDTO;
import br.com.rafaelsa.api.dtos.users.response.UserResponseDTO;
import br.com.rafaelsa.api.entities.User;
import br.com.rafaelsa.api.exceptions.UserOperationException;
import br.com.rafaelsa.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public AuthenticationManager authenticationManager;

  public ResponseEntity<UserResponseDTO> register(RegisterUserDTO registerUserDTO) {
    User user = new User();

    user.setName(registerUserDTO.name());
    user.setEmail(registerUserDTO.email());
    user.setPassword(passwordEncoder.encode(registerUserDTO.password()));

    userRepository.save(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.fromEntity(user));
  }

  public User authenticate(LoginUserDTO loginUserDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserDTO.email(),
            loginUserDTO.password()
        )
    );

    return userRepository.findByEmail(loginUserDTO.email()).orElseThrow(() -> new UserOperationException("E-mail é/ou senha incorreta", HttpStatus.UNAUTHORIZED));
  }
}
