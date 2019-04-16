package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.Noun;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.domain.Category;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.repository.NounRepository;
import com.voronin.english.util.WriteFileToDisk;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Noun service test class.
 *
 * @author Alexey Voronin.
 * @since 08.04.2019.
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
     * Mock PartOfSpeechService.
     */
    private PartOfSpeechService partOfSpeechService = mock(PartOfSpeechService.class);

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
    private NounService nounService =
            new NounService(
                    writeFileToDisk,
                    partOfSpeechService,
                    translationService,
                    phraseService,
                    imageService,
                    categoryService,
                    nounRepository);

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
     * Class for test.
     */
    private PartOfSpeech partOfSpeech = new PartOfSpeech();

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
        partOfSpeech.setId(uuid);
        partOfSpeech.setPartOfSpeech("speech");
        noun.setWord("word");
        cardFilled = new CardFilled("word", "transcription", "translation",
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
     * should return list Word.
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
     * When getNounsByCategoryId should return list of words.
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
     * When call getNounByName should return Word.
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
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage)).thenReturn(file);
        when(categoryService.getCategoryByName(cardFilled.getCategory())).thenReturn(category);
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);

        assertThat(nounService.prepareAndSave(cardFilled, multipartFile).getWord(), is(noun.getWord()));
        verify(nounRepository, times(1)).save(noun);
    }
}
