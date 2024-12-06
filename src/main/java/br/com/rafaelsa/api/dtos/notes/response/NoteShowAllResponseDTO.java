package br.com.rafaelsa.api.dtos.notes.response;

import br.com.rafaelsa.api.dtos.links.LinkResponseDTO;
import br.com.rafaelsa.api.dtos.tags.response.TagResponseDTO;
import br.com.rafaelsa.api.entities.Note;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public record NoteShowAllResponseDTO(Long id,
                                     String title,
                                     String description,
                                     Long userId,
                                     LocalDateTime createdAt,
                                     LocalDateTime updatedAt,
                                     List<TagResponseDTO> tags,
                                     List<LinkResponseDTO> links) {
  public static NoteResponseDTO fromEntity(Note note) {
    List<TagResponseDTO> tags = note.getTagList().stream()
        .map(TagResponseDTO::fromEntity)
        .sorted(Comparator.comparing(TagResponseDTO::name, Comparator.nullsFirst(Comparator.naturalOrder())))
        .toList();

    List<LinkResponseDTO> links = note.getLinkList().stream()
        .map(LinkResponseDTO::fromEntity)
        .sorted(Comparator.comparing(LinkResponseDTO::createdAt, Comparator.nullsFirst(Comparator.naturalOrder())))
        .toList();

    return new NoteResponseDTO(note.getId(),
        note.getTitle(),
        note.getDescription(),
        note.getUser().getId(),
        note.getCreatedAt(),
        note.getUpdatedAt(),
        tags,
        links);
  }
}
