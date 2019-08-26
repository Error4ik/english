package com.voronin.sentence.repository;

import com.voronin.sentence.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TimeOfPhrase repository.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Repository
public interface TimeRepository extends JpaRepository<Time, UUID> {
}
