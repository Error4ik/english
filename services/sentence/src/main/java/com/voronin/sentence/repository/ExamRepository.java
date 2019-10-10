package com.voronin.sentence.repository;

import com.voronin.sentence.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Exam repository.
 *
 * @author Alexey Voronin.
 * @since 04.10.2019.
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {

    /**
     * Get Exam by id.
     *
     * @param id exam id.
     * @return Exam.
     */
    Exam getExamById(UUID id);
}
