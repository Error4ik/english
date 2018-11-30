package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.*;
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
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class WordService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WordService.class);

    @Autowired
    private WriteFileToDisk writeFileToDisk;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PartOfSpeechService partOfSpeechService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private PhraseService phraseService;

    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    public List<Word> getWords() {
        return this.wordRepository.findAll();
    }

    public List<Word> getWordsByCategory(final UUID categoryId) {
        return this.wordRepository.getAllByCategoryId(categoryId);
    }

    public Word prepareAndSave(final CardFilled card, final MultipartFile photo) {
        Word word = new Word(card.getWord(),
                card.getTranscription(),
                this.categoryService.getCategoryByName(card.getCategory()),
                this.partOfSpeechService.getPartOfSpeechByName(card.getPartOfSpeech()),
                card.getDescription()
        );
        File file = this.writeFileToDisk.writeImage(photo, pathToSaveImage);
        word.setImage(this.imageService.save(new Image(file.getName(), file.getAbsolutePath())));
        this.wordRepository.save(word);
        this.translationService.saveAll(getTranslation(card, word));
        this.phraseService.saveAll(getPhrases(card, word));
        return word;
    }

    private List<Phrase> getPhrases(final CardFilled card, final Word word) {
        Phrase p1 = new Phrase(card.getFirstPhrase(), card.getFirstPhraseTranslation(), word);
        Phrase p2 = new Phrase(card.getSecondPhrase(), card.getSecondPhraseTranslation(), word);
        return Lists.newArrayList(p1, p2);
    }

    private List<Translation> getTranslation(final CardFilled card, final Word word) {
        List<Translation> translations = new ArrayList<>();
        for (String s : card.getTranslation().split(",")) {
            Translation t = new Translation(s, word);
            translations.add(t);
        }
        return translations;
    }
}
