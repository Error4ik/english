package com.voronin.sentence.repository;

import com.voronin.sentence.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * QuestionRepository.
 *
 * @author Alexey Voronin.
 * @since 08.10.2019.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
