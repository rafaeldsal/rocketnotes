package br.com.rafaelsa.api.servicies;

import br.com.rafaelsa.api.dtos.notes.request.NoteRequestDTO;
import br.com.rafaelsa.api.dtos.notes.response.NoteResponseDTO;
import br.com.rafaelsa.api.entities.Link;
import br.com.rafaelsa.api.entities.Note;
import br.com.rafaelsa.api.entities.Tag;
import br.com.rafaelsa.api.entities.User;
import br.com.rafaelsa.api.exceptions.UserOperationException;
import br.com.rafaelsa.api.repositories.NoteRepository;
import br.com.rafaelsa.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private NoteRepository noteRepository;

  public ResponseEntity<NoteResponseDTO> create(Long userId, NoteRequestDTO noteRequestDTO) {
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

    return ResponseEntity.status(HttpStatus.OK).body(NoteResponseDTO.fromEntity(note));
  }
}
