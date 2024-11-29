package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.notes.request.NoteRequestDTO;
import br.com.rafaelsa.api.dtos.notes.response.NoteResponseDTO;
import br.com.rafaelsa.api.servicies.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notes")
public class NoteController {

  @Autowired
  private NoteService noteService;

  @PostMapping("/{user_id}")
  public ResponseEntity<NoteResponseDTO> createNote(@PathVariable(name = "user_id") Long userId,
                                                    @RequestBody NoteRequestDTO noteRequestDTO) {
    return noteService.create(userId, noteRequestDTO);
  }
}
