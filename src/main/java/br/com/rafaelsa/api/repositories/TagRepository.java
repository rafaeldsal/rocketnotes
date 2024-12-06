package br.com.rafaelsa.api.repositories;

import br.com.rafaelsa.api.entities.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

  public Tag findTagByName(String name);
  public List<Tag> findTagByUserId(Long userId);
}
