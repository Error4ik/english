package com.voronin.sentence.repository;

import com.voronin.sentence.domain.Category;
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
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    /**
     * Get category by name.
     *
     * @param name category name.
     * @return PhraseCategory.
     */
    Category getByName(String name);
}
