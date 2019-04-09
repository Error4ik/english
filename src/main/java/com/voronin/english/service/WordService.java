package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.AnyWord;
import com.voronin.english.domain.Phrase;
import com.voronin.english.domain.Translation;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Word;
import com.voronin.english.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * Constructor.
     *
     * @param wordRepository      word repository.
     * @param partOfSpeechService part of speech service.
     * @param translationService  translation service.
     * @param phraseService       phrase service.
     */
    @Autowired
    public WordService(
            final WordRepository wordRepository,
            final PartOfSpeechService partOfSpeechService,
            final TranslationService translationService,
            final PhraseService phraseService) {
        this.wordRepository = wordRepository;
        this.partOfSpeechService = partOfSpeechService;
        this.translationService = translationService;
        this.phraseService = phraseService;
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
     * @param card  Filled model of the word.
     * @return Word.
     */
    public Word prepareAndSave(final CardFilled card) {
        logger.debug(String.format("Arguments - word - %s", card));
        PartOfSpeech partOfSpeech = this.partOfSpeechService.getPartOfSpeechByName(card.getPartOfSpeech().trim());
        Word word = new Word(card.getWord(), card.getTranscription(), partOfSpeech, card.getDescription());
        this.save(word);
        logger.info(String.format("Word save without image, %s", word));
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() + 1);
        this.partOfSpeechService.save(partOfSpeech);
        this.translationService.saveAll(getTranslation(card, word));
        this.phraseService.saveAll(getPhrases(card, word));
        logger.debug(String.format("Return - %s", word));
        return word;
    }

    /**
     * Get phrases.
     *
     * @param card Filled model of the word.
     * @param word word.
     * @return List of Phrase.
     */
    private List<Phrase> getPhrases(final CardFilled card, final AnyWord word) {
        Phrase p1 = new Phrase(card.getFirstPhrase(), card.getFirstPhraseTranslation(), word);
        Phrase p2 = new Phrase(card.getSecondPhrase(), card.getSecondPhraseTranslation(), word);
        return Lists.newArrayList(p1, p2);
    }

    /**
     * Get translations.
     *
     * @param card Filled model of the word.
     * @param word word.
     * @return list of Translation.
     */
    private List<Translation> getTranslation(final CardFilled card, final AnyWord word) {
        List<Translation> translations = new ArrayList<>();
        for (String s : card.getTranslation().split(",")) {
            Translation t = new Translation(s, word);
            translations.add(t);
        }
        return translations;
    }
}
