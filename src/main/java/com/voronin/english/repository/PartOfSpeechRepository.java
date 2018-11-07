package com.voronin.english.repository;

import com.voronin.english.domain.PartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface PartOfSpeechRepository extends JpaRepository<PartOfSpeech, UUID> {
    public PartOfSpeech getPartOfSpeechByPartOfSpeech(final String partOfSpeech);
}
