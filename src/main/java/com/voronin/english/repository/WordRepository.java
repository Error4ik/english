package com.voronin.english.repository;

import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
     * Get words by category id.
     *
     * @param categoryId category id.
     * @return list of words.
     */
    List<Word> getAllByCategoryId(UUID categoryId);

    /**
     * Get word by id.
     *
     * @param uuid id;
     * @return word.
     */
    Word getWordById(UUID uuid);

    /**
     * Get word by name.
     *
     * @param word name.
     * @return word.
     */
    Word getWordByWord(String word);

    /**
     * Get the words that are included in the list.
     *
     * @param words list.
     * @return list of words.
     */
    List<Word> getAllByWordIn(Collection<String> words);

    /**
     * Get words by part of speech.
     *
     * @param partOfSpeech part of speech.
     * @return list of words.
     */
    List<Word> getAllByPartOfSpeech(PartOfSpeech partOfSpeech);
}
