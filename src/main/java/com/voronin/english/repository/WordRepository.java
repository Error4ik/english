package com.voronin.english.repository;

import com.voronin.english.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {
    public List<Word> getAllByCategoryId(final UUID categoryId);
}