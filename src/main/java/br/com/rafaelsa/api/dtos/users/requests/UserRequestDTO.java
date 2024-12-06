package br.com.rafaelsa.api.dtos.users.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequestDTO(String name,
                             String email,
                             String password,
                             @JsonProperty("old_password") String oldPassword) {

}
