package com.voronin.english.repository;

import com.voronin.english.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Category repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    /**
     * Get category by name.
     *
     * @param name name.
     * @return category.
     */
    Category getCategoryByName(String name);

    /**
     * Get all category order by name.
     *
     * @return list of category.
     */
    List<Category> findAllByOrderByNameAsc();
}
