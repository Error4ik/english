package com.voronin.english.repository;

import com.voronin.english.domain.TypeOfPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TypeOfPhrase repository.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Repository
public interface TypeOfPhraseRepository extends JpaRepository<TypeOfPhrase, UUID> {
}
