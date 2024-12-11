package br.com.rafaelsa.api.dtos.users.requests;

public record RegisterUserDTO(String name,
                              String email,
                              String password) {
}
