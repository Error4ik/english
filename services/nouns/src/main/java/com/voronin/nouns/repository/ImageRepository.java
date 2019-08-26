package com.voronin.nouns.repository;

import com.voronin.nouns.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Image repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
}
