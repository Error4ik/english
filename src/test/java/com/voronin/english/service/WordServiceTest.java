package com.voronin.english.service;

import com.voronin.english.domain.Word;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.repository.WordRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * WordService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class WordServiceTest {

    /**
     * Mock WordRepository.
     */
    @MockBean
    private WordRepository wordRepository = mock(WordRepository.class);

    /**
     * Mock PartOfSpeechService.
     */
    @MockBean
    private PartOfSpeechService partOfSpeechService = mock(PartOfSpeechService.class);

    /**
     * Mock TranslationService.
     */
    @MockBean
    private TranslationService translationService = mock(TranslationService.class);

    /**
     * Mock PhraseService.
     */
    @MockBean
    private PhraseService phraseService = mock(PhraseService.class);

    /**
     * The class object under test.
     */
    private WordService wordService =
            new WordService(
                    wordRepository,
                    partOfSpeechService,
                    translationService,
                    phraseService);

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
        verify(wordRepository, times(1)).findAll();
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
        verify(wordRepository, times(1)).getAllByPartOfSpeechId(uuid, pageable);
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
        verify(wordRepository, times(1)).getWordById(word.getId());
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
        verify(wordRepository, times(1)).getNumberOfRecordsByPartOfSpeechId(uuid);
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
        verify(wordRepository, times(1)).save(word);
    }

    /**
     * When call prepareAndSave should return saved Word.
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
        verify(partOfSpeechService, times(1)).getPartOfSpeechByName(anyString());
        verify(wordRepository, times(1)).save(any(Word.class));
        verify(partOfSpeechService, times(1)).save(partOfSpeech);
        verify(translationService, times(1)).saveAll(anyList());
        verify(phraseService, times(1)).saveAll(anyList());
    }
}
