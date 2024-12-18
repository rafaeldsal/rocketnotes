package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.users.requests.UserRequestDTO;
import br.com.rafaelsa.api.dtos.users.response.UserResponseDTO;
import br.com.rafaelsa.api.servicies.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserControllers {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
    return userService.create(userRequestDTO);
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
    try {
      return ResponseEntity.ok().body(userService.update(userRequestDTO));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
