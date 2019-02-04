package com.voronin.english.repository;

import com.voronin.english.domain.TimeOfPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TimeOfPhrase repository.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Repository
public interface TimeOfPhraseRepository extends JpaRepository<TimeOfPhrase, UUID> {
}
