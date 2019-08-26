package com.voronin.words.repository;

import com.voronin.words.domain.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Word repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {

    /**
     * Get word by id.
     *
     * @param uuid id;
     * @return word.
     */
    Word getWordById(UUID uuid);

    /**
     * Get words by part of speech.
     *
     * @param partOfSpeechId part of speech.
     * @param pageable       pageable.
     * @return list of words.
     */
    List<Word> getAllByPartOfSpeechId(UUID partOfSpeechId, Pageable pageable);

    /**
     * Get the number of records by part of speech id.
     *
     * @param partOfSpeechId partOfSpeech id.
     * @return number of records.
     */
    @Query("select count (w.id) from words as w where w.partOfSpeech.id = ?1")
    long getNumberOfRecordsByPartOfSpeechId(UUID partOfSpeechId);
}
