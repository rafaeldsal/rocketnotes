package br.com.rafaelsa.api.dtos.notes.request;

import br.com.rafaelsa.api.entities.Link;
import br.com.rafaelsa.api.entities.Tag;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record NoteRequestDTO(
    @NotBlank(message = "O campo [title] não pode ser vazio.")
    String title,

    @NotBlank(message = "O campo [description] não pode ser vazio.")
    String description,

    List<Tag> tags,

    List<Link> links) {
}
