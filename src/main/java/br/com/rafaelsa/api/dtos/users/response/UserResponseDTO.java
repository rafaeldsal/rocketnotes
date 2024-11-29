package br.com.rafaelsa.api.dtos.users.response;

import br.com.rafaelsa.api.entities.User;

public record UserResponseDTO(Long id, String name, String email) {
  public static UserResponseDTO fromEntity(User user) {
    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }
}
