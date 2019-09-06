package com.voronin.words.repository;

import com.voronin.words.domain.Exam;
import com.voronin.words.domain.PartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Exam repository.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {
    /**
     * @param uuid id.
     * @return Exam.
     */
    Exam getExamById(UUID uuid);

    /**
     * @param partOfSpeech part of speech.
     * @return Exam.
     */
    Exam getExamByPartOfSpeech(PartOfSpeech partOfSpeech);

    /**
     * @param name exam name.
     * @return Exam.
     */
    Exam getExamByName(String name);
}
