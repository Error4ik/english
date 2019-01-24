package com.voronin.english.repository;

import com.voronin.english.domain.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Translation repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface TranslationRepository extends JpaRepository<Translation, UUID> {
}
