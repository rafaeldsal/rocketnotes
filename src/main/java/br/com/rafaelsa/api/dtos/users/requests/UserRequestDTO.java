package br.com.rafaelsa.api.dtos.users.requests;

import br.com.rafaelsa.api.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(
    @NotBlank(message = "O campo [name] não pode ser vazio.")
    String name,

    @Email(message = "O campo [email] deve conter um e-mail válido")
    String email,

    @Length(min = 8, max = 100, message = "A senha deve conter de 8 a 100 caracteres.")
    String password,

    UserRole role,

    @JsonProperty("old_password") String oldPassword) {

}
