package com.voronin.sentence.repository;

import com.voronin.sentence.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TypeOfPhrase repository.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, UUID> {
}
