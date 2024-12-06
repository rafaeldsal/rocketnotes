package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.dtos.notes.response.NoteResponseDTO;
import br.com.rafaelsa.api.dtos.tags.response.TagResponseDTO;
import br.com.rafaelsa.api.entities.Tag;
import br.com.rafaelsa.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TagService {

  @Autowired
  private TagRepository tagRepository;

  public ResponseEntity<List<TagResponseDTO>> index(Long userId) {
    List<Tag> tagList = tagRepository.findTagByUserId(userId);

    if (tagList.isEmpty()) {
      throw new RuntimeException();
    }

    List<TagResponseDTO> tagResponseDTOS = tagList.stream()
        .map(TagResponseDTO::fromEntity)
        .sorted(Comparator.comparing(TagResponseDTO::id, Comparator.nullsFirst(Comparator.naturalOrder())))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(tagResponseDTOS);
  }
}
