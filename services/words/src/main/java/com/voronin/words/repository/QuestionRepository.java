package com.voronin.words.repository;

import com.voronin.words.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Question repository.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
