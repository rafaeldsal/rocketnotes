package br.com.rafaelsa.api.controllers;

import br.com.rafaelsa.api.dtos.tags.response.TagResponseDTO;
import br.com.rafaelsa.api.servicies.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

  @Autowired
  private TagService tagService;


  @GetMapping("{user_id}")
  public ResponseEntity<List<TagResponseDTO>> showTags(@PathVariable(name = "user_id") Long userId){
    return tagService.index(userId);
  }
}
