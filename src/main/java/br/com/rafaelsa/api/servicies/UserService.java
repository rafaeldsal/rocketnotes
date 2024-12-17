package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.dtos.users.requests.UserRequestDTO;
import br.com.rafaelsa.api.dtos.users.response.UserResponseDTO;
import br.com.rafaelsa.api.entities.User;
import br.com.rafaelsa.api.exceptions.EmailAlreadyExistsException;
import br.com.rafaelsa.api.exceptions.PasswordMismatchException;
import br.com.rafaelsa.api.exceptions.UserOperationException;
import br.com.rafaelsa.api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private UserRepository userRepository;

  public ResponseEntity<UserResponseDTO> create(UserRequestDTO userRequestDTO) {

    logger.info("Recebendo solicitação para criar usuário com email: {}", userRequestDTO.email());

    if (userRepository.existsByEmail(userRequestDTO.email())) {
      throw new UserOperationException("Usuário já cadastrado.", HttpStatus.BAD_REQUEST);
    }

    User user = new User();
    user.setName(userRequestDTO.name());
    user.setEmail(userRequestDTO.email());
    user.setPassword(encoder.encode(userRequestDTO.password()));

    userRepository.save(user);

    logger.info("Usuário criado com sucesso: {}", userRequestDTO.email());
    return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.fromEntity(user));
  }

  public ResponseEntity<UserResponseDTO> update(Long userId, UserRequestDTO userRequestDTO) {

    logger.info("Recebendo solicitação para atualizar usuário com email: {}", userRequestDTO.email());

    User getUser = userRepository.findById(userId)
        .orElseThrow(() -> new UserOperationException("Usuário não encontrado.", HttpStatus.CONFLICT));

    userRepository.findByEmail(userRequestDTO.email())
        .filter(user -> !user.getId().equals(userId))
        .ifPresent(user -> {
          throw new EmailAlreadyExistsException("Este e-mail já está em uso.", HttpStatus.UNAUTHORIZED);
        });

    getUser.setName(userRequestDTO.name() != null ? userRequestDTO.name() : getUser.getName());
    getUser.setEmail(userRequestDTO.email() != null ? userRequestDTO.email() : getUser.getEmail());

    String oldPassword = userRequestDTO.oldPassword();
    String password = userRequestDTO.password();

    if (password != null) {
      if (oldPassword == null) {
        throw new PasswordMismatchException("Informe a senha atual.", HttpStatus.BAD_REQUEST);
      }

      boolean validPassword = encoder.matches(oldPassword, getUser.getPassword());

      if (!validPassword) {
        throw new PasswordMismatchException("Senha digitada não confere.", HttpStatus.BAD_REQUEST);
      }

      String encodedPassword = encoder.encode(password);

      getUser.setPassword(encodedPassword);
    }

    userRepository.save(getUser);

    return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.fromEntity(getUser));
  }
}
