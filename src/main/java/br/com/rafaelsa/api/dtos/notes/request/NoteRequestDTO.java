package br.com.rafaelsa.api.dtos.notes.request;

import br.com.rafaelsa.api.entities.Link;
import br.com.rafaelsa.api.entities.Tag;

import java.util.List;

public record NoteRequestDTO(String title, String description, List<Tag> tags, List<Link> links) {
}
