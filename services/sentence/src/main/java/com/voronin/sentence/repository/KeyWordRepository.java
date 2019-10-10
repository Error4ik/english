package com.voronin.sentence.repository;

import com.voronin.sentence.domain.KeyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * KeyWord repository.
 *
 * @author Alexey Voronin.
 * @since 08.10.2019.
 */
@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord, UUID> {

    /**
     * Find KeyWord in list of Words.
     *
     * @param keyWords list of KeyWords.
     * @return List of KeyWords.
     */
    List<KeyWord> findByWordIn(Collection<String> keyWords);
}
