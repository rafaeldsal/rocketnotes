package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.dtos.notes.request.NoteRequestDTO;
import br.com.rafaelsa.api.dtos.notes.response.NoteResponseDTO;
import br.com.rafaelsa.api.entities.Link;
import br.com.rafaelsa.api.entities.Note;
import br.com.rafaelsa.api.entities.Tag;
import br.com.rafaelsa.api.entities.User;
import br.com.rafaelsa.api.exceptions.NoteOperationException;
import br.com.rafaelsa.api.exceptions.UserOperationException;
import br.com.rafaelsa.api.repositories.NoteRepository;
import br.com.rafaelsa.api.repositories.TagRepository;
import br.com.rafaelsa.api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NoteService {

  private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private NoteRepository noteRepository;

  public ResponseEntity<NoteResponseDTO> create(Long userId, NoteRequestDTO noteRequestDTO) {

    logger.info("Recebendo solicitação para criar nota do usuário id: {}", userId);

    User user = userRepository.findById(userId)
        .orElseThrow(
            () -> new UserOperationException("Usuário não encontrado", HttpStatus.BAD_REQUEST));

    Note note = new Note();
    note.setTitle(noteRequestDTO.title());
    note.setDescription(noteRequestDTO.description());
    note.setUser(user);

    List<Tag> tagList = noteRequestDTO.tags().stream()
        .map(tagItem -> {
          Tag tag = new Tag();
          tag.setName(tagItem.getName());
          tag.setNote(note);
          tag.setUser(user);

          return tag;
        })
        .toList();

    note.setTagList(tagList);

    List<Link> linkList = noteRequestDTO.links().stream()
        .map(linkItens -> {
          Link link = new Link();
          link.setUrl(linkItens.getUrl());
          link.setNote(linkItens.getNote());
          link.setNote(note);
          return link;
        })
        .toList();

    note.setLinkList(linkList);

    noteRepository.save(note);

    logger.info("Nota criada com sucesso para o usuário com email: {}", note.getUser().getEmail());

    return ResponseEntity.status(HttpStatus.OK).body(NoteResponseDTO.fromEntity(note));
  }

  public ResponseEntity<NoteResponseDTO> show(Long noteId) {

    logger.info("Recebendo solicitação para exibir nota de id: {}", noteId);
    Note note = noteRepository.findById(noteId)
        .orElseThrow(() -> new NoteOperationException("Nota não encontrada", HttpStatus.BAD_REQUEST));

    return ResponseEntity.status(HttpStatus.OK).body(NoteResponseDTO.fromEntity(note));
  }

  private List<Note> getNotes(Long userId, String title, List<String> tags) {

    logger.info("Recuperando notas");

    if (userId != null) {
      if (!tags.isEmpty()) {
        if (title != null) {
          return noteRepository.findByTagsUserIdAndTitleContaining(tags, userId, title);
        }
        return noteRepository.findByTagsAndUserId(tags, userId);
      }
    }

    if (userId != null) {
      if (title != null) {
        return noteRepository.findByUserIdAndTitleContainingIgnoreCase(userId, title);
      }
      return noteRepository.findByUserId(userId);
    }

    if (title != null) {
      return noteRepository.findByTitleContainingIgnoreCase(title);
    }

    return noteRepository.findAll();
  }

  private ResponseEntity<List<NoteResponseDTO>> buildResponse(List<Note> noteList) {
    if(noteList.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    List<NoteResponseDTO> noteResponseDTOS = noteList.stream()
        .map(NoteResponseDTO::fromEntity)
        .sorted(Comparator.comparing(NoteResponseDTO::title, Comparator.nullsFirst(Comparator.naturalOrder())))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(noteResponseDTOS);
  }

  public ResponseEntity<List<NoteResponseDTO>> index(Long userId, String title, List<String> tags) {

    logger.info("Recebendo solicitação para exibir notas usuário: {}", userId);

    List<String> safeTags = tags != null ? tags : List.of();
    List<Note> noteList = getNotes(userId, title, safeTags);
    return buildResponse(noteList);
  }

  public ResponseEntity<Void> delete(Long noteId) {
    logger.info("Recebendo solicitação para deletar nota de id: {}", noteId);

    Note note = noteRepository.findById(noteId)
        .orElseThrow(() -> new NoteOperationException("Não existe nota para o ID informado", HttpStatus.BAD_REQUEST));

    noteRepository.delete(note);

    return ResponseEntity.ok().build();
  }
}
