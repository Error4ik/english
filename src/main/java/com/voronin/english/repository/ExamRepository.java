package com.voronin.english.repository;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.12.2018.
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {

    Exam getExamById(final UUID uuid);

    Exam getExamByCategory(final Category category);

    Exam getExamByName(final String name);
}
