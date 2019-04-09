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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

/**
 * Noun service test class.
 *
 * @author Alexey Voronin.
 * @since 08.04.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(NounService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class NounServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private NounService nounService;

    /**
     * Mock WriteFileToDisk.
     */
    @MockBean
    private WriteFileToDisk writeFileToDisk;

    /**
     * Mock WordRepository.
     */
    @MockBean
    private NounRepository nounRepository;

    /**
     * Mock CategoryService.
     */
    @MockBean
    private CategoryService categoryService;

    /**
     * Mock PartOfSpeechService.
     */
    @MockBean
    private PartOfSpeechService partOfSpeechService;

    /**
     * Mock ImageService.
     */
    @MockBean
    private ImageService imageService;

    /**
     * Mock TranslationService.
     */
    @MockBean
    private TranslationService translationService;

    /**
     * Mock PhraseService.
     */
    @MockBean
    private PhraseService phraseService;

    /**
     * Mock MultipartFile.
     */
    @MockBean
    private MultipartFile multipartFile;

    /**
     * Mock File.
     */
    @MockBean
    private File file;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock Pageable.
     */
    @MockBean
    private Pageable pageable;

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
    }

    /**
     * When call prepareAndSave should return saved Noun.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnNoun() throws Exception {
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage)).thenReturn(file);
        when(categoryService.getCategoryByName(cardFilled.getCategory())).thenReturn(category);
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);

        assertThat(nounService.prepareAndSave(cardFilled, multipartFile).getWord(), is(noun.getWord()));
    }
}
