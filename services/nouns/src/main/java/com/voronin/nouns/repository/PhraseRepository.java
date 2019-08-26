package com.voronin.nouns.repository;

import com.voronin.nouns.domain.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Phrase repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface PhraseRepository extends JpaRepository<Phrase, UUID> {
}
