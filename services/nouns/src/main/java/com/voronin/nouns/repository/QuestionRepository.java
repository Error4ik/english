package com.voronin.nouns.repository;

import com.voronin.nouns.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Question repository class.
 *
 * @author Alexey Voronin.
 * @since 30.08.2019.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
