package com.voronin.english.repository;

import com.voronin.english.domain.PhraseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * PhraseCategory repository.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Repository
public interface PhraseCategoryRepository extends JpaRepository<PhraseCategory, UUID> {

    /**
     * Get category by name.
     *
     * @param name category name.
     * @return PhraseCategory.
     */
    PhraseCategory getByName(String name);
}
