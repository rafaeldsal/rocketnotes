package br.com.rafaelsa.api.dtos.notes.response;

import br.com.rafaelsa.api.dtos.links.LinkResponseDTO;
import br.com.rafaelsa.api.dtos.tags.response.TagResponseDTO;
import br.com.rafaelsa.api.entities.Note;

import java.time.LocalDateTime;
import java.util.List;

public record NoteResponseDTO(Long id,
                              String title,
                              Long userId,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              List<TagResponseDTO> tags,
                              List<LinkResponseDTO> links) {

  public static NoteResponseDTO fromEntity(Note note) {
    List<TagResponseDTO> tags = note.getTagList().stream()
        .map(TagResponseDTO::fromEntity)
        .toList();

    List<LinkResponseDTO> links = note.getLinkList().stream()
        .map(LinkResponseDTO::fromEntity)
        .toList();

    return new NoteResponseDTO(note.getId(),
        note.getTitle(),
        note.getUser().getId(),
        note.getCreatedAt(),
        note.getUpdatedAt(),
        tags,
        links);
  }
}
