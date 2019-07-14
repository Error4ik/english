package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.Translation;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Word;
import com.voronin.english.repository.WordRepository;
import com.voronin.english.util.PhrasesAndTranslationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Word service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class WordService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(WordService.class);

    /**
     * Word repository.
     */
    private final WordRepository wordRepository;

    /**
     * Part of speech service.
     */
    private final PartOfSpeechService partOfSpeechService;

    /**
     * Translation service.
     */
    private final TranslationService translationService;

    /**
     * Phrase service.
     */
    private final PhraseService phraseService;

    /**
     * Util class, get phrases and translation.
     */
    private final PhrasesAndTranslationUtil phrasesAndTranslationUtil;

    /**
     * Constructor.
     *
     * @param wordRepository        word repository.
     * @param partOfSpeechService   part of speech service.
     * @param translationService    translation service.
     * @param phraseService         phrase service.
     * @param phrasesAndTranslation util class get phrases and translation.
     */
    @Autowired
    public WordService(
            final WordRepository wordRepository,
            final PartOfSpeechService partOfSpeechService,
            final TranslationService translationService,
            final PhraseService phraseService,
            final PhrasesAndTranslationUtil phrasesAndTranslation) {
        this.wordRepository = wordRepository;
        this.partOfSpeechService = partOfSpeechService;
        this.translationService = translationService;
        this.phraseService = phraseService;
        this.phrasesAndTranslationUtil = phrasesAndTranslation;
    }

    /**
     * Get all words.
     *
     * @return list of Word.
     */
    public List<Word> getWords() {
        return this.wordRepository.findAll();
    }

    /**
     * Get words by part of speech id.
     *
     * @param id       id.
     * @param pageable Pageable.
     * @return list of Word.
     */
    public List<Word> getWordsByPartOfSpeechId(final UUID id, final Pageable pageable) {
        return this.wordRepository.getAllByPartOfSpeechId(id, pageable);
    }

    /**
     * Get word by id.
     *
     * @param uuid word id.
     * @return Word.
     */
    public Word getWordById(final UUID uuid) {
        return this.wordRepository.getWordById(uuid);
    }

    /**
     * Delete word.
     *
     * @param id id.
     */
    @Transactional
    public void deleteWord(final UUID id) {
        logger.debug(String.format("Arguments - wordId - %s", id));
        Word word = this.wordRepository.getWordById(id);
        PartOfSpeech partOfSpeech = word.getPartOfSpeech();
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() - 1);
        this.wordRepository.delete(word);
        logger.debug(String.format("Delete - word - %s", word));
        this.partOfSpeechService.save(partOfSpeech);
        logger.debug(String.format("Change partOfSpeech number of word and save - partOfSpeech - %s", partOfSpeech));
    }

    /**
     * Get number of records by part of speech id.
     *
     * @param partOfSpeechId partOfSpeechId.
     * @return number of records.
     */
    public long getNumberOfRecordsByPartOfSpeechId(final UUID partOfSpeechId) {
        return this.wordRepository.getNumberOfRecordsByPartOfSpeechId(partOfSpeechId);
    }

    /**
     * Save word.
     *
     * @param word word.
     * @return Word.
     */
    public Word save(final Word word) {
        return this.wordRepository.save(word);
    }

    /**
     * Prepare and save Word.
     *
     * @param card Filled model of the word.
     * @return Word.
     */
    @Transactional
    public Word prepareAndSave(final CardFilled card) {
        logger.debug(String.format("Arguments - word - %s", card));
        PartOfSpeech partOfSpeech = this.partOfSpeechService.getPartOfSpeechByName(card.getPartOfSpeech().trim());
        Word word = new Word(
                card.getWord(),
                card.getTranscription().trim(),
                partOfSpeech,
                card.getDescription().trim());
        this.save(word);
        logger.info(String.format("Word save without image, %s", word));
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() + 1);
        this.partOfSpeechService.save(partOfSpeech);
        this.translationService.saveAll(phrasesAndTranslationUtil.getTranslation(card, word));
        this.phraseService.saveAll(phrasesAndTranslationUtil.getPhrases(card, word));
        logger.debug(String.format("Return - %s", word));
        return word;
    }

    /**
     * Edit word.
     *
     * @param cardFilled Filled model of the word.
     * @param wordId     word id fo edit.
     */
    public Word editWordAndSave(final CardFilled cardFilled, final String wordId) {
        logger.debug(String.format("Arguments - CardFilled - %s, wordId - %s", cardFilled, wordId));
        Word word = this.getWordById(UUID.fromString(wordId));
        word.setWord(cardFilled.getWord());
        word.setTranscription(cardFilled.getTranscription());
        word.setDescription(cardFilled.getDescription());
        if (!word.getPartOfSpeech().getPartOfSpeech().equalsIgnoreCase(cardFilled.getPartOfSpeech())) {
            changePartOfSpeech(cardFilled, word);
        }
        this.phrasesAndTranslationUtil.updatePhrase(
                Lists.newArrayList(word.getPhrases()),
                this.phrasesAndTranslationUtil.getPhrases(cardFilled, word));
        this.phraseService.saveAll(Lists.newArrayList(word.getPhrases()));
        for (Translation translation : word.getTranslations()) {
            this.translationService.delete(translation);
        }
        word.setTranslations(
                this.translationService.saveAll(
                        this.phrasesAndTranslationUtil.getTranslation(cardFilled, word)));
        logger.debug(String.format("Return word - %s", word));
        return this.save(word);
    }

    /**
     * Changing part of speech for word.
     *
     * @param cardFilled Filled model of the word.
     * @param word       word for changing.
     */
    @Transactional
    private void changePartOfSpeech(final CardFilled cardFilled, final Word word) {
        PartOfSpeech partOfSpeech = word.getPartOfSpeech();
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() - 1);
        this.partOfSpeechService.save(partOfSpeech);
        partOfSpeech = this.partOfSpeechService.getPartOfSpeechByName(cardFilled.getPartOfSpeech());
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() + 1);
        word.setPartOfSpeech(this.partOfSpeechService.save(partOfSpeech));
    }
}
