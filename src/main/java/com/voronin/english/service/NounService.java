package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.Noun;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.domain.Category;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Image;
import com.voronin.english.domain.Phrase;
import com.voronin.english.domain.Translation;
import com.voronin.english.domain.AnyWord;
import com.voronin.english.repository.NounRepository;
import com.voronin.english.util.WriteFileToDisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Noun service.
 *
 * @author Alexey Voronin.
 * @since 05.04.2019.
 */
@Service
public class NounService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(WordService.class);

    /**
     * Part of speech service.
     */
    private final PartOfSpeechService partOfSpeechService;

    /**
     * Class for writing a file to disk.
     */
    private final WriteFileToDisk writeFileToDisk;

    /**
     * Translation service.
     */
    private final TranslationService translationService;

    /**
     * Phrase service.
     */
    private final PhraseService phraseService;

    /**
     * Image service.
     */
    private final ImageService imageService;

    /**
     * Category service.
     */
    private final CategoryService categoryService;

    /**
     * Noun repository.
     */
    private NounRepository nounRepository;

    /**
     * Default path to save image to disk.
     */
    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    /**
     * @param writeFileToDisk     class for writing a file to disk.
     * @param partOfSpeechService partOfSpeech service.
     * @param translationService  translation service.
     * @param phraseService       phrase service.
     * @param imageService        image service.
     * @param categoryService     category service.
     * @param nounRepository      noun repository.
     */
    public NounService(
            final WriteFileToDisk writeFileToDisk,
            final PartOfSpeechService partOfSpeechService,
            final TranslationService translationService,
            final PhraseService phraseService,
            final ImageService imageService,
            final CategoryService categoryService,
            final NounRepository nounRepository) {
        this.writeFileToDisk = writeFileToDisk;
        this.partOfSpeechService = partOfSpeechService;
        this.translationService = translationService;
        this.phraseService = phraseService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.nounRepository = nounRepository;
    }

    /**
     * Save noun.
     *
     * @param noun Noun.
     * @return saved Noun.
     */
    public Noun save(final Noun noun) {
        return this.nounRepository.save(noun);
    }

    /**
     * Get nouns by category id with pageable.
     *
     * @param categoryId category id.
     * @return list of Noun.
     */
    public List<Noun> getNounsByCategoryId(final UUID categoryId, final Pageable pageable) {
        return this.nounRepository.getAllByCategoryId(categoryId, pageable);
    }

    /**
     * Get nouns by category id.
     *
     * @param categoryId category id.
     * @return list of Noun.
     */
    public List<Noun> getNounsByCategoryId(final UUID categoryId) {
        return this.nounRepository.getAllByCategoryId(categoryId);
    }

    /**
     * Get number of records by category id.
     *
     * @param categoryId category id.
     * @return number of records.
     */
    public long getNumberOfRecordsByCategoryId(final UUID categoryId) {
        return this.nounRepository.getNumberOfRecordsByCategoryId(categoryId);
    }

    /**
     * Get noun by name.
     *
     * @param name word name.
     * @return Noun.
     */
    public Noun getNounByName(final String name) {
        return this.nounRepository.getNounByWord(name);
    }

    /**
     * Get list noun by names.
     *
     * @param names list of name.
     * @return List of Noun.
     */
    public List<Noun> getNounsByNames(final List<String> names) {
        return this.nounRepository.getAllByWordIn(names);
    }

    /**
     * Prepare and save Noun.
     *
     * @param card Filled model of the word.
     * @return Noun.
     */
    public Noun prepareAndSave(final CardFilled card, final MultipartFile photo) {
        logger.debug(String.format("Arguments - word - %s", card));
        PartOfSpeech partOfSpeech = this.partOfSpeechService.getPartOfSpeechByName(card.getPartOfSpeech().trim());
        Category category = this.categoryService.getCategoryByName(card.getCategory());
        Noun noun = new Noun(card.getWord(), card.getTranscription(), partOfSpeech, category, card.getDescription());
        File file = this.writeFileToDisk.writeImage(photo, pathToSaveImage);
        noun.setImage(this.imageService.save(new Image(file.getName(), file.getAbsolutePath())));
        this.save(noun);
        logger.debug(String.format("Save noun - %s", noun));
        partOfSpeech.setNumberOfWords(partOfSpeech.getNumberOfWords() + 1);
        this.partOfSpeechService.save(partOfSpeech);
        logger.debug(String.format("Save part of speech - %s", partOfSpeech));
        this.translationService.saveAll(getTranslation(card, noun));
        this.phraseService.saveAll(getPhrases(card, noun));
        category.setWordsCount(category.getWordsCount() + 1);
        this.categoryService.save(category);
        logger.debug(String.format("Save category - %s", category));
        logger.debug(String.format("Return - %s", noun));
        return noun;
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
