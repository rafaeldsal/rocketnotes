package br.com.rafaelsa.api.dtos.tags.response;

import br.com.rafaelsa.api.entities.Tag;

public record TagResponseDTO(Long id, String name, Long userId, Long noteId) {
  public static TagResponseDTO fromEntity(Tag tag) {
    return new TagResponseDTO(tag.getId(), tag.getName(), tag.getUser().getId(), tag.getNote().getId());
  }
}
