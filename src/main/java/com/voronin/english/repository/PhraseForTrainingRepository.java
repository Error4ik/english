package com.voronin.english.repository;

import com.voronin.english.domain.PhraseForTraining;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * @return list of PhraseForTraining.
     */
    List<PhraseForTraining> getAllByPhraseCategoryId(UUID phraseCategoryId);
}
