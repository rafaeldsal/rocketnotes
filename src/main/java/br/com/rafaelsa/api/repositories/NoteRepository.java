package br.com.rafaelsa.api.repositories;

import br.com.rafaelsa.api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  public List<Note> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);

  public List<Note> findByTitleContainingIgnoreCase(String title);

  @Query("SELECT DISTINCT n FROM Note n JOIN n.tagList t WHERE t.name IN :tags AND n.user.id = :userId")
  List<Note> findByTagsAndUserId(@Param("tags") List<String> tags, @Param("userId") Long userId);

  public List<Note> findByUserId(Long userId);

  @Query("SELECT DISTINCT n FROM Note n JOIN n.tagList t WHERE t.name IN :tags AND n.user.id = :userId AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
  List<Note> findByTagsUserIdAndTitleContaining(@Param("tags") List<String> tags, @Param("userId") Long userId, @Param("title") String title);

}
