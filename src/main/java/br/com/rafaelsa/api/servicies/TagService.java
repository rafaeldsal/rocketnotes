package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.dtos.tags.response.TagResponseDTO;
import br.com.rafaelsa.api.entities.Tag;
import br.com.rafaelsa.api.exceptions.TagsNotFoundException;
import br.com.rafaelsa.api.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TagService {

  private static final Logger logger = LoggerFactory.getLogger(TagService.class);

  @Autowired
  private TagRepository tagRepository;

  public ResponseEntity<List<TagResponseDTO>> index() {

    String userContext = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = Long.parseLong(userContext);

    logger.info("Recebendo solicitação para apresentar tags do usuário de id: {}", userId);

    List<Tag> tagList = tagRepository.findTagByUserId(userId);

    if (tagList.isEmpty()) {
      throw new TagsNotFoundException("Não tem tags para o usuário de ID " + userId, HttpStatus.BAD_REQUEST);
    }

    List<TagResponseDTO> tagResponseDTOS = tagList.stream()
        .map(TagResponseDTO::fromEntity)
        .sorted(Comparator.comparing(TagResponseDTO::id, Comparator.nullsFirst(Comparator.naturalOrder())))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(tagResponseDTOS);
  }
}
