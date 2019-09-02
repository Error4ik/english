package com.voronin.nouns.service;

import com.google.common.collect.Lists;
import com.voronin.nouns.domain.Noun;
import com.voronin.nouns.domain.CardFilled;
import com.voronin.nouns.domain.Category;
import com.voronin.nouns.domain.Image;
import com.voronin.nouns.domain.Translation;
import com.voronin.nouns.repository.NounRepository;
import com.voronin.nouns.utils.PhrasesAndTranslationUtil;
import com.voronin.nouns.utils.WriteFileToDisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
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
    private final Logger logger = LoggerFactory.getLogger(NounService.class);

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
     * Util class, get phrases and translation.
     */
    private final PhrasesAndTranslationUtil phrasesAndTranslationUtil;

    /**
     * Default path to save image to disk.
     */
    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    /**
     * @param writeFileToDisk       class for writing a file to disk.
     * @param translationService    translation service.
     * @param phraseService         phrase service.
     * @param imageService          image service.
     * @param categoryService       category service.
     * @param nounRepository        noun repository.
     * @param phrasesAndTranslation util class get phrases and translation.
     */
    public NounService(
            final WriteFileToDisk writeFileToDisk,
            final TranslationService translationService,
            final PhraseService phraseService,
            final ImageService imageService,
            final CategoryService categoryService,
            final NounRepository nounRepository,
            final PhrasesAndTranslationUtil phrasesAndTranslation) {
        this.writeFileToDisk = writeFileToDisk;
        this.translationService = translationService;
        this.phraseService = phraseService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.nounRepository = nounRepository;
        this.phrasesAndTranslationUtil = phrasesAndTranslation;
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
     * Get all nouns.
     *
     * @return List of nouns.
     */
    public List<Noun> getNouns() {
        return this.nounRepository.findAll();
    }

    /**
     * Delete noun.
     *
     * @param id id.
     */
    @Transactional
    public void deleteNoun(final UUID id) {
        logger.debug(String.format("Arguments - nounId - %s", id));
        Noun noun = this.nounRepository.getNounById(id);
        Category category = noun.getCategory();
        Image image = noun.getImage();
        this.nounRepository.delete(noun);
        logger.debug(String.format("Delete - %s", noun));
        category.setNounsCount(category.getNounsCount() - 1);
        this.categoryService.save(category);
        File file = new File(image.getUrl());
        boolean isDelete = file.delete();
        logger.debug(String.format("Delete image from filesystem - %s", isDelete));
    }

    /**
     * Get noun by name.
     *
     * @param name noun name.
     * @return Noun.
     */
    public Noun getNounByName(final String name) {
        return this.nounRepository.getNounByNoun(name);
    }

    /**
     * Get noun by id.
     *
     * @param id id.
     * @return noun by id.
     */
    public Noun getNounById(final UUID id) {
        return this.nounRepository.getOne(id);
    }

    /**
     * Get list noun by names.
     *
     * @param names list of name.
     * @return List of Noun.
     */
    public List<Noun> getNounsByNames(final List<String> names) {
        return this.nounRepository.getAllByNounIn(names);
    }

    /**
     * Prepare and save Noun.
     *
     * @param card  Filled model of the noun.
     * @param photo noun image.
     * @return Noun.
     */
    @Transactional
    public Noun prepareAndSave(final CardFilled card, final MultipartFile photo) {
        logger.debug(String.format("Arguments - noun - %s", card));
        Category category = this.categoryService.getCategoryByName(card.getCategory());
        Noun noun = new Noun(
                card.getNoun(),
                card.getTranscription().trim(),
                card.getPartOfSpeech().trim(),
                category,
                card.getDescription().trim());
        File file = this.writeFileToDisk.writeImage(photo, pathToSaveImage, noun.getNoun());
        noun.setImage(this.imageService.save(new Image(file.getName(), file.getAbsolutePath())));
        this.save(noun);
        logger.debug(String.format("Save noun - %s", noun));
        this.translationService.saveAll(phrasesAndTranslationUtil.getTranslation(card, noun));
        this.phraseService.saveAll(phrasesAndTranslationUtil.getPhrases(card, noun));
        category.setNounsCount(category.getNounsCount() + 1);
        this.categoryService.save(category);
        logger.debug(String.format("Return - %s", noun));
        return noun;
    }

    /**
     * Edit noun.
     *
     * @param cardFilled filled model of noun.
     * @param photo      image if any.
     * @param nounId     the ID of the noun you want to edit.
     * @return saved Noun.
     */
    public Noun editNounAndSave(final CardFilled cardFilled, final MultipartFile photo, final String nounId) {
        logger.debug(String.format("Arguments - CardFilled - %s,  - %s, nounId - %s", cardFilled, photo, nounId));
        Noun noun = this.getNounById(UUID.fromString(nounId));
        noun.setNoun(cardFilled.getNoun());
        noun.setTranscription(cardFilled.getTranscription());
        noun.setDescription(cardFilled.getDescription().trim());
        if (!noun.getCategory().getName().equalsIgnoreCase(cardFilled.getCategory())) {
            changeCategoryForNoun(cardFilled, noun);
        }
        if (photo != null) {
            changeImageForNoun(photo, noun);
        }
        this.phrasesAndTranslationUtil.updatePhrase(
                Lists.newArrayList(noun.getPhrases()),
                this.phrasesAndTranslationUtil.getPhrases(cardFilled, noun));
        this.phraseService.saveAll(Lists.newArrayList(noun.getPhrases()));
        for (Translation translation : noun.getTranslations()) {
            this.translationService.delete(translation);
        }
        noun.setTranslations(
                this.translationService.saveAll(
                        this.phrasesAndTranslationUtil.getTranslation(cardFilled, noun)));
        logger.debug(String.format("Return noun - %s", noun));
        return this.save(noun);
    }

    /**
     * Changing image for noun.
     *
     * @param photo image.
     * @param noun  noun.
     */
    @Transactional
    private void changeImageForNoun(final MultipartFile photo, final Noun noun) {
        Image oldImage = noun.getImage();
        File fileForOldImage = new File(oldImage.getUrl());
        fileForOldImage.delete();
        File fileForNewImage = this.writeFileToDisk.writeImage(photo, pathToSaveImage, noun.getNoun());
        oldImage.setName(fileForNewImage.getName());
        oldImage.setUrl(fileForNewImage.getAbsolutePath());
        this.imageService.save(oldImage);
    }

    /**
     * Changing category for noun.
     *
     * @param cardFilled filled model of noun.
     * @param noun       noun.
     */
    @Transactional
    private void changeCategoryForNoun(final CardFilled cardFilled, final Noun noun) {
        Category category = noun.getCategory();
        category.setNounsCount(category.getNounsCount() - 1);
        this.categoryService.save(category);
        category = this.categoryService.getCategoryByName(cardFilled.getCategory());
        category.setNounsCount(category.getNounsCount() + 1);
        noun.setCategory(this.categoryService.save(category));
    }
}
