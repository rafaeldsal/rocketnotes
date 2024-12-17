package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.notes.request.NoteRequestDTO;
import br.com.rafaelsa.api.dtos.notes.response.NoteResponseDTO;
import br.com.rafaelsa.api.servicies.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notes")
public class NoteController {

  @Autowired
  private NoteService noteService;

  @PostMapping("/{user_id}")
  public ResponseEntity<NoteResponseDTO> createNote(@PathVariable(name = "user_id") Long userId,
                                                    @Valid @RequestBody NoteRequestDTO noteRequestDTO) {
    return noteService.create(userId, noteRequestDTO);
  }

  @GetMapping("/{note_id}")
  public ResponseEntity<NoteResponseDTO> showNote(@PathVariable(name = "note_id") Long noteId) {
    return noteService.show(noteId);
  }

  @GetMapping
  public ResponseEntity<List<NoteResponseDTO>> index(@RequestParam(required = false, value = "user_id") Long userId,
                                                     @RequestParam(required = false, value = "title") String title,
                                                     @RequestParam(required = false, value = "tags") List<String> tags) {
    return noteService.index(userId, title, tags);
  }

  @DeleteMapping("/{note_id}")
  public ResponseEntity<Void> removeNote(@PathVariable(name = "note_id") Long noteId) {
    return noteService.delete(noteId);
  }
}
