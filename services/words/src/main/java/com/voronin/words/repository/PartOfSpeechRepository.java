package com.voronin.words.repository;

import com.voronin.words.domain.PartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Part of speech repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface PartOfSpeechRepository extends
        JpaRepository<PartOfSpeech, UUID> {

    /**
     * Get part of speech by name.
     *
     * @param name name.
     * @return part of speech.
     */
    PartOfSpeech getPartOfSpeechByName(String name);

    /**
     * Get part of speech by id.
     *
     * @param id id.
     * @return part of speech.
     */
    PartOfSpeech getById(UUID id);
}
