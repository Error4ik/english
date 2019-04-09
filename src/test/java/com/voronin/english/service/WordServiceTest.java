package com.voronin.english.service;

import com.voronin.english.domain.Word;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.repository.WordRepository;
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
     * Mock WordRepository.
     */
    @MockBean
    private WordRepository wordRepository;

    /**
     * Mock PartOfSpeechService.
     */
    @MockBean
    private PartOfSpeechService partOfSpeechService;

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
    private Word word = new Word();

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
    private List<Word> list = new ArrayList<>();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        partOfSpeech.setId(uuid);
        partOfSpeech.setPartOfSpeech("speech");
        word.setWord("word");
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

    /**
     * When call getWordsByPartOfSpeech should return List of Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetWordsByPartOfSpeechShouldReturnListWord() throws Exception {
        when(wordRepository.getAllByPartOfSpeechId(uuid, pageable)).thenReturn(list);

        assertThat(wordService.getWordsByPartOfSpeechId(uuid, pageable), is(list));
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
     * When call getNumberOfRecordsByPartOfSpeechId should return number of records.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNumberOfRecordsByPartOfSpeechIdShouldReturnNumberOfRecords() throws Exception {
        final long numberOfRecords = 10;
        when(wordRepository.getNumberOfRecordsByPartOfSpeechId(uuid)).thenReturn(numberOfRecords);

        assertThat(wordService.getNumberOfRecordsByPartOfSpeechId(uuid), is(numberOfRecords));
    }

    /**
     * When call method save should return saved word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveWordShouldReturnSavedWord() throws Exception {
        when(this.wordRepository.save(word)).thenReturn(word);

        assertThat(wordService.save(word), is(word));
    }

    /**
     * When call prepareAndSave without photo should return saved Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveWithoutPhotoShouldReturnWord() throws Exception {
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);
        final int numberOfWords = 1;

        Word w = wordService.prepareAndSave(cardFilled);
        assertThat(w.getWord(), is(word.getWord()));
        assertThat(partOfSpeech.getNumberOfWords(), is(numberOfWords));
    }
}
