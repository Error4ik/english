package com.voronin.english.repository;

import com.voronin.english.domain.PhraseForTraining;
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
public interface PhraseForTrainingRepository extends JpaRepository<PhraseForTraining, UUID> {

    /**
     * Get list PhraseForTraining by PhraseCategory id.
     *
     * @param phraseCategoryId id.
     * @param pageable         Pageable.
     * @return list of PhraseForTraining.
     */
    List<PhraseForTraining> getAllByPhraseCategoryId(UUID phraseCategoryId, Pageable pageable);

    /**
     * Get the number of records by PhraseCategory id.
     *
     * @param phraseCategoryId PhraseCategory id.
     * @return number of records.
     */
    @Query("select count (p.id) from phrase_for_training as p where p.phraseCategory.id = ?1")
    long getNumberOfRecordsByPhraseCategoryId(UUID phraseCategoryId);
}
