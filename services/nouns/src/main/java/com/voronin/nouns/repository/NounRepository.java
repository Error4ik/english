package com.voronin.nouns.repository;

import com.voronin.nouns.domain.Noun;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Noun repository.
 *
 * @author Alexey Voronin.
 * @since 05.04.2019.
 */
@Repository
public interface NounRepository extends JpaRepository<Noun, UUID> {

    /**
     * Get nouns by category id with pageable.
     *
     * @param categoryId category id.
     * @param pageable   pageable.
     * @return list of nouns.
     */
    List<Noun> getAllByCategoryId(UUID categoryId, Pageable pageable);

    /**
     * Get nouns by category id.
     *
     * @param categoryId category id.
     * @return list of nouns.
     */
    List<Noun> getAllByCategoryId(UUID categoryId);

    /**
     * Get the nouns that are included in the list.
     *
     * @param nouns list.
     * @return list of Noun.
     */
    List<Noun> getAllByNounIn(Collection<String> nouns);

    /**
     * Get noun by name.
     *
     * @param noun name.
     * @return Noun.
     */
    Noun getNounByNoun(String noun);

    /**
     * Get noun by id.
     *
     * @param uuid id.
     * @return Noun.
     */
    Noun getNounById(UUID uuid);

    /**
     * Get the number of records by category id.
     *
     * @param categoryId category id.
     * @return number of records.
     */
    @Query("select count (n.id) from nouns as n where n.category.id = ?1")
    long getNumberOfRecordsByCategoryId(UUID categoryId);
}
