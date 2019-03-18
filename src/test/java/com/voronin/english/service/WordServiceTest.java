package com.voronin.english.service;

import com.voronin.english.domain.*;
import com.voronin.english.repository.WordRepository;
import com.voronin.english.util.WriteFileToDisk;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

/**
 * WordService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WordService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class WordServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private WordService wordService;

    /**
     * Mock WriteFileToDisk.
     */
    @MockBean
    private WriteFileToDisk writeFileToDisk;

    /**
     * Mock WordRepository.
     */
    @MockBean
    private WordRepository wordRepository;

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
     * Path to save image.
     */
    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    /**
     * Class for test.
     */
    private Word word = new Word();

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
     * Class for test.
     */
    private Image image = new Image();

    /**
     * List Word for test.
     */
    private List<Word> list = new ArrayList<>();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        category.setId(uuid);
        category.setName("category");
        partOfSpeech.setId(uuid);
        partOfSpeech.setPartOfSpeech("speech");
        image.setName("image");
        image.setUrl("path");
        word.setWord("word");
        word.setImage(image);
        cardFilled = new CardFilled("word", "transcription", "translation",
                "category", "speech", "firstPhrase", "secondPhrase",
                "firstPhraseTranslation", "secondPhraseTranslation",
                "description");
        list.add(word);
    }

    /**
     * When Call getWords should return list of Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetWordsShouldReturnListWords() throws Exception {
        when(wordRepository.findAll()).thenReturn(list);

        assertThat(wordService.getWords(), is(list));
    }

//    /**
//     * When call getWordsByCategory should return list Word.
//     *
//     * @throws Exception exception.
//     */
//    @Test
//    public void whenGetWordsByCategoryShouldReturnListWords() throws Exception {
//        when(wordRepository.getAllByCategoryId(category.getId())).thenReturn(list);
//
//        assertThat(wordService.getWordsByCategory(category.getId()), is(list));
//    }

    /**
     * When call prepareAndSave with photo should return saved Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveWithPhotoShouldReturnWord() throws Exception {
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage)).thenReturn(file);
        when(categoryService.getCategoryByName(cardFilled.getCategory())).thenReturn(category);
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);

        assertThat(wordService.prepareAndSave(cardFilled, multipartFile).getWord(), is(word.getWord()));
    }

    /**
     * When call prepareAndSave without photo should return saved Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveWithoutPhotoShouldReturnWord() throws Exception {
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);

        Word w = wordService.prepareAndSave(cardFilled, null);
        assertThat(w.getWord(), is(word.getWord()));
    }

    /**
     * When call getWordByName should return Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetWordByNameShouldReturnWord() throws Exception {
        when(wordRepository.getWordByWord(word.getWord())).thenReturn(word);

        assertThat(wordService.getWordByName(word.getWord()), is(word));
    }

    /**
     * When call getWordById should return Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetWordByIdShouldReturnWord() throws Exception {
        when(wordRepository.getWordById(word.getId())).thenReturn(word);

        assertThat(wordService.getWordById(word.getId()), is(word));
    }

    /**
     * When call getWordsByNames should return list of Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetWordsByNamesShouldReturnListWord() throws Exception {
        List<Word> words = Lists.newArrayList(word, word, word);
        List<String> list = Lists.newArrayList("word", "word", "word");
        when(wordRepository.getAllByWordIn(list)).thenReturn(words);

        assertThat(wordService.getWordsByNames(list), is(words));
    }

//    /**
//     * When call getWordsByPartOfSpeech should return List of Word.
//     *
//     * @throws Exception exception.
//     */
//    @Test
//    public void whenGetWordsByPartOfSpeechShouldReturnListWord() throws Exception {
//        when(wordRepository.getAllByPartOfSpeechId(uuid)).thenReturn(list);
//
//        assertThat(wordService.getWordsByPartOfSpeechId(uuid), is(list));
//    }
}
