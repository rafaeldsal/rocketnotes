package br.com.rafaelsa.api.dtos.authentication;

public record AuthenticationResponseDTO(String accessToken, Long expiresIn) {
}
