package com.voronin.sentence.repository;

import com.voronin.sentence.domain.Sentence;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * PhraseForTraining repository.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Repository
public interface SentenceRepository extends JpaRepository<Sentence, UUID> {

    /**
     * Get list PhraseForTraining by PhraseCategory id.
     *
     * @param categoryId     id.
     * @param pageable           Pageable.
     * @return list of PhraseForTraining.
     */
    List<Sentence> getAllByCategoryId(UUID categoryId, Pageable pageable);

    /**
     * Get the number of records by PhraseCategory id.
     *
     * @param sentenceCategoryId PhraseCategory id.
     * @return number of records.
     */
    @Query("select count (s.id) from sentences as s where s.category.id = ?1")
    long getNumberOfRecordsBySentenceCategoryId(UUID sentenceCategoryId);
}
