package com.voronin.english.repository;

import com.voronin.english.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Quest repository.
 *
 * @author Alexey Voronin.
 * @since 10.12.2018.
 */
@Repository
public interface QuestRepository extends JpaRepository<Question, UUID> {
}
