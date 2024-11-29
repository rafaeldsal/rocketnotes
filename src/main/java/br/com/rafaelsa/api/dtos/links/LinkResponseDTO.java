package br.com.rafaelsa.api.dtos.links;

import br.com.rafaelsa.api.entities.Link;

import java.time.LocalDateTime;

public record LinkResponseDTO(Long id, String url, LocalDateTime created_at) {
  public static LinkResponseDTO fromEntity(Link link) {
    return new LinkResponseDTO(link.getId(), link.getUrl(), link.getCreatedAt());
  }
}
