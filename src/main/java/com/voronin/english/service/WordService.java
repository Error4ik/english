package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.Word;
import com.voronin.english.domain.Category;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.domain.Translation;
import com.voronin.english.domain.Phrase;
import com.voronin.english.domain.Image;
import com.voronin.english.repository.WordRepository;
import com.voronin.english.util.WriteFileToDisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
     * Class for writing a file to disk.
     */
    private final WriteFileToDisk writeFileToDisk;

    /**
     * Word repository.
     */
    private final WordRepository wordRepository;

    /**
     * Category service.
     */
    private final CategoryService categoryService;

    /**
     * Part of speech service.
     */
    private final PartOfSpeechService partOfSpeechService;

    /**
     * Image service.
     */
    private final ImageService imageService;

    /**
     * Translation service.
     */
    private final TranslationService translationService;

    /**
     * Phrase service.
     */
    private final PhraseService phraseService;

    /**
     * Default path to save image to disk.
     */
    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    /**
     * Constructor.
     *
     * @param writeFileToDisk     class for writing a file to disk.
     * @param wordRepository      word repository.
     * @param categoryService     category service.
     * @param partOfSpeechService part of speech service.
     * @param imageService        image service.
     * @param translationService  translation service.
     * @param phraseService       phrase service.
     */
    @Autowired
    public WordService(
            final WriteFileToDisk writeFileToDisk,
            final WordRepository wordRepository,
            final CategoryService categoryService,
            final PartOfSpeechService partOfSpeechService,
            final ImageService imageService,
            final TranslationService translationService,
            final PhraseService phraseService) {
        this.writeFileToDisk = writeFileToDisk;
        this.wordRepository = wordRepository;
        this.categoryService = categoryService;
        this.partOfSpeechService = partOfSpeechService;
        this.imageService = imageService;
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
     * Get words by category id.
     *
     * @param categoryId category id.
     * @return list of Word.
     */
    public List<Word> getWordsByCategory(final UUID categoryId) {
        return this.wordRepository.getAllByCategoryId(categoryId);
    }

    /**
     * Get words by part of speech id.
     *
     * @param id id.
     * @return list of Word.
     */
    public List<Word> getWordsByPartOfSpeechId(final UUID id) {
        return this.wordRepository.getAllByPartOfSpeechId(id);
    }

    /**
     * Get word by name.
     *
     * @param name word name.
     * @return Word.
     */
    public Word getWordByName(final String name) {
        return this.wordRepository.getWordByWord(name);
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
     * Get list word by names.
     *
     * @param names list of name.
     * @return List of Word.
     */
    public List<Word> getWordsByNames(final List<String> names) {
        return this.wordRepository.getAllByWordIn(names);
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
     * @param photo word image.
     * @return Word.
     */
    public Word prepareAndSave(final CardFilled card, final MultipartFile photo) {
        PartOfSpeech partOfSpeech = this.partOfSpeechService.getPartOfSpeechByName(card.getPartOfSpeech());
        Word word = new Word(card.getWord(), card.getTranscription(), null, partOfSpeech, card.getDescription());
        if (photo != null) {
            saveWordWithImage(card, photo, word);
        } else {
            this.save(word);
            logger.info(String.format("Word save without image, %s", word));
        }
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() + 1);
        this.partOfSpeechService.save(partOfSpeech);
        this.translationService.saveAll(getTranslation(card, word));
        this.phraseService.saveAll(getPhrases(card, word));
        return word;
    }

    /**
     * Method prepare and save word with image.
     *
     * @param card  Filled model of the word.
     * @param photo word image.
     * @param word  word.
     */
    private void saveWordWithImage(final CardFilled card, final MultipartFile photo, final Word word) {
        Category category = this.categoryService.getCategoryByName(card.getCategory());
        word.setCategory(category);
        File file = this.writeFileToDisk.writeImage(photo, pathToSaveImage);
        word.setImage(this.imageService.save(new Image(file.getName(), file.getAbsolutePath())));
        this.save(word);
        category.setWordsCount(category.getWordsCount() + 1);
        this.categoryService.save(category);
        logger.info(String.format("Word save with image, %s", word));
    }

    /**
     * Get phrases.
     *
     * @param card Filled model of the word.
     * @param word word.
     * @return List of Phrase.
     */
    private List<Phrase> getPhrases(final CardFilled card, final Word word) {
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
    private List<Translation> getTranslation(final CardFilled card, final Word word) {
        List<Translation> translations = new ArrayList<>();
        for (String s : card.getTranslation().split(",")) {
            Translation t = new Translation(s, word);
            translations.add(t);
        }
        return translations;
    }
}
