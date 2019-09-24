package com.voronin.nouns.service;

import com.google.common.collect.Lists;
import com.voronin.nouns.domain.*;
import com.voronin.nouns.repository.NounRepository;
import com.voronin.nouns.utils.PhrasesAndTranslationUtil;
import com.voronin.nouns.utils.WriteFileToDisk;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Noun service test class.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
 */
public class NounServiceTest {

    /**
     * Mock WriteFileToDisk.
     */
    private WriteFileToDisk writeFileToDisk = mock(WriteFileToDisk.class);

    /**
     * Mock WordRepository.
     */
    private NounRepository nounRepository = mock(NounRepository.class);

    /**
     * Mock CategoryService.
     */
    private CategoryService categoryService = mock(CategoryService.class);

    /**
     * Mock ImageService.
     */
    private ImageService imageService = mock(ImageService.class);

    /**
     * Mock TranslationService.
     */
    private TranslationService translationService = mock(TranslationService.class);

    /**
     * Mock PhraseService.
     */
    private PhraseService phraseService = mock(PhraseService.class);

    /**
     * Mock PhrasesAndTranslation.
     */
    private PhrasesAndTranslationUtil phrasesAndTranslation = mock(PhrasesAndTranslationUtil.class);

    /**
     * Mock File.
     */
    private File file = mock(File.class);

    /**
     * Mock Pageable.
     */
    private Pageable pageable = mock(Pageable.class);

    /**
     * The class object under test.
     */
    private NounService nounService = new NounService(
            writeFileToDisk,
            translationService,
            phraseService,
            imageService,
            categoryService,
            nounRepository,
            phrasesAndTranslation);

    /**
     * Path to save image.
     */
    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    /**
     * Class for test.
     */
    private Noun noun = new Noun();

    /**
     * Class for test.
     */
    private Category category = new Category();

    /**
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * Class for test.
     */
    private CardFilled cardFilled;

    /**
     * List Word for test.
     */
    private List<Noun> list = new ArrayList<>();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        category.setId(uuid);
        category.setName("category");
        category.setNounsCount(2);
        noun.setWord("noun");
        noun.setImage(new Image("test", "test"));
        noun.setCategory(category);
        noun.setPartOfSpeech("speech");
        cardFilled = new CardFilled("noun", "transcription", "translation",
                "category", "speech", "firstPhrase", "secondPhrase",
                "firstPhraseTranslation", "secondPhraseTranslation",
                "description");
        list.add(noun);
    }

    /**
     * When save Noun should return saved Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveNounShouldReturnSavedNoun() throws Exception {
        when(nounRepository.save(any(Noun.class))).thenReturn(noun);

        assertThat(nounService.save(noun), is(noun));
        verify(nounRepository, times(1)).save(any(Noun.class));
    }


    /**
     * When call getNounsByCategoryId with limit and page
     * should return list of Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounsByCategoryIdWithLimitAndPageShouldReturnListNouns() throws Exception {
        when(nounRepository.getAllByCategoryId(category.getId(), pageable)).thenReturn(list);

        assertThat(nounService.getNounsByCategoryId(category.getId(), pageable), is(list));
        verify(nounRepository, times(1)).getAllByCategoryId(category.getId(), pageable);
    }

    /**
     * When getNounsByCategoryId should return list of Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounsByCategoryIdShouldReturnListOfNouns() throws Exception {
        when(nounRepository.getAllByCategoryId(uuid)).thenReturn(list);

        assertThat(nounService.getNounsByCategoryId(uuid), is(list));
        verify(nounRepository, times(1)).getAllByCategoryId(uuid);
    }

    /**
     * When call getNumberOfRecordsByCategoryId should return number of records.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNumberOfRecordsByCategoryIdShouldReturnNumberOfRecords() throws Exception {
        final long numberOfRecords = 10;
        when(nounRepository.getNumberOfRecordsByCategoryId(uuid)).thenReturn(numberOfRecords);

        assertThat(nounService.getNumberOfRecordsByCategoryId(uuid), is(numberOfRecords));
        verify(nounRepository, times(1)).getNumberOfRecordsByCategoryId(uuid);
    }

    /**
     * When call getNounByName should return Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounByNameShouldReturnNoun() throws Exception {
        when(nounRepository.getNounByWord(noun.getWord())).thenReturn(noun);

        assertThat(nounService.getNounByName(noun.getWord()), is(noun));
        verify(nounRepository, times(1)).getNounByWord(noun.getWord());
    }

    /**
     * When call getNounsByNames should return list of Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounsByNamesShouldReturnListNouns() throws Exception {
        List<Noun> nouns = Lists.newArrayList(noun, noun, noun);
        List<String> listOfNames = Lists.newArrayList("word", "word", "word");
        when(nounRepository.getAllByWordIn(listOfNames)).thenReturn(nouns);

        assertThat(nounService.getNounsByNames(listOfNames), is(nouns));
        verify(nounRepository, times(1)).getAllByWordIn(listOfNames);
    }

    /**
     * When call prepareAndSave should return saved Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnNoun() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage, noun.getWord())).thenReturn(file);
        when(categoryService.getCategoryByName(cardFilled.getCategory())).thenReturn(category);

        assertThat(nounService.prepareAndSave(cardFilled, multipartFile).getWord(), is(noun.getWord()));
        verify(nounRepository, times(1)).save(any(Noun.class));
    }

    /**
     * When call getNouns should return list of nouns and call method findAll NounRepository class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounsShouldReturnListOfNouns() throws Exception {
        List<Noun> nounList = Lists.newArrayList(noun);
        when(this.nounRepository.findAll()).thenReturn(nounList);

        assertThat(this.nounService.getNouns(), is(nounList));
        verify(this.nounRepository, times(1)).findAll();
    }

    /**
     * WHen call getNounById should return Noun and call method getOne() NounRepository class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounByIdShouldReturnNoun() throws Exception {
        when(this.nounRepository.getOne(uuid)).thenReturn(noun);

        assertThat(this.nounService.getNounById(uuid), is(noun));
        verify(this.nounRepository, times(1)).getOne(uuid);
    }

    /**
     * When call deleteNoun method should call delete method NounRepository class once
     * and change numberOfWord Category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenDeleteNounShouldCallDeleteMethodNounOnce() throws Exception {
        when(this.nounRepository.getNounById(uuid)).thenReturn(noun);
        final int expectedValue = 1;

        this.nounService.deleteNoun(uuid);

        assertThat(noun.getCategory().getNounsCount(), is(expectedValue));
        verify(this.nounRepository, times(1)).delete(noun);
        verify(this.categoryService, times(1)).save(noun.getCategory());
    }

    /**
     * When call editNounAndSave without changes category and images should return changed noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenEditNounAndSaveWithoutChangeCategoryAndImagesShouldReturnChangedNoun() throws Exception {
        when(this.nounService.getNounById(uuid)).thenReturn(noun);
        when(this.nounService.save(any(Noun.class))).thenReturn(noun);
        noun.setPhrases(new HashSet<>(Lists.newArrayList(new Phrase())));
        noun.setTranslations(Lists.newArrayList(new Translation()));

        this.nounService.editNounAndSave(cardFilled, null, uuid.toString());

        assertThat(noun.getTranscription(), is(cardFilled.getTranscription()));
        assertThat(noun.getDescription(), is(cardFilled.getDescription()));
    }

    /**
     * When editNounAndSave with changes category and
     * without a images should return changed noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenEditNounAndSaveWithChangesCategoryShouldReturnChangedNounAndChangeCategory() throws Exception {
        noun.setPhrases(new HashSet<>(Lists.newArrayList(new Phrase())));
        noun.setTranslations(Lists.newArrayList(new Translation()));
        this.cardFilled.setCategory("new category");
        final int oldCategoryWordCount = 1;
        final int newCategoryWordCount = 1;
        final Category expectedCategory = new Category();
        expectedCategory.setName("new category");
        when(this.nounService.getNounById(uuid)).thenReturn(noun);
        when(this.nounService.save(any(Noun.class))).thenReturn(noun);
        when(this.categoryService.getCategoryByName(cardFilled.getCategory())).thenReturn(expectedCategory);
        when(this.categoryService.save(any(Category.class))).thenReturn(expectedCategory);

        this.nounService.editNounAndSave(cardFilled, null, uuid.toString());

        assertThat(noun.getCategory().getName(), is(expectedCategory.getName()));
        assertThat(noun.getTranscription(), is(cardFilled.getTranscription()));
        assertThat(noun.getDescription(), is(cardFilled.getDescription()));
        assertThat(this.category.getNounsCount(), is(oldCategoryWordCount));
        assertThat(expectedCategory.getNounsCount(), is(newCategoryWordCount));
    }

    /**
     * When editNounAndSave with changes image
     * should return changed noun and change images.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenEditNounAndSaveWithChangesImagesShouldReturnChangedNounAndChangeImages() throws Exception {
        when(this.nounService.getNounById(uuid)).thenReturn(noun);
        when(this.nounService.save(any(Noun.class))).thenReturn(noun);
        noun.setPhrases(new HashSet<>(Lists.newArrayList(new Phrase())));
        noun.setTranslations(Lists.newArrayList(new Translation()));
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage, noun.getWord())).thenReturn(file);
        when(file.getName()).thenReturn("new name file");
        when(file.getAbsolutePath()).thenReturn("new absolute path");

        this.nounService.editNounAndSave(cardFilled, multipartFile, uuid.toString());

        assertThat(this.noun.getImage().getName(), is(this.file.getName()));
        assertThat(this.noun.getImage().getUrl(), is(this.file.getAbsolutePath()));
        assertThat(noun.getTranscription(), is(cardFilled.getTranscription()));
        assertThat(noun.getDescription(), is(cardFilled.getDescription()));
    }

}