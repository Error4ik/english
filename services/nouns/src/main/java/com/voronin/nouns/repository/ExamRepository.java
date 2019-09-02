package com.voronin.nouns.repository;

import com.voronin.nouns.domain.Category;
import com.voronin.nouns.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Exam repository class.
 *
 * @author Alexey Voronin.
 * @since 30.08.2019.
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {

    /**
     * Get exam by id.
     *
     * @param uuid id.
     * @return exam.
     */
    Exam getExamById(UUID uuid);

    /**
     * Get exam by category.
     *
     * @param category category.
     * @return exam.
     */
    Exam getExamByCategory(Category category);

    /**
     * Get exam by name.
     *
     * @param name name.
     * @return exam.
     */
    Exam getExamByName(String name);
}
