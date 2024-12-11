package br.com.rafaelsa.api.dtos.users.response;

import br.com.rafaelsa.api.entities.User;

public record LoginResponse(User user, String token, long expiresIn) {
}
