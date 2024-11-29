package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.users.requests.UserRequestDTO;
import br.com.rafaelsa.api.dtos.users.response.UserResponseDTO;
import br.com.rafaelsa.api.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
    return userService.create(userRequestDTO);
  }

  @PutMapping("/{user_id}")
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable(name = "user_id") Long userId,
                                                    @RequestBody UserRequestDTO userRequestDTO) {
    return userService.update(userId, userRequestDTO);
  }

}
