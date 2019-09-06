package com.voronin.words.service;

import com.google.common.collect.Lists;
import com.voronin.words.domain.Word;
import com.voronin.words.domain.PartOfSpeech;
import com.voronin.words.domain.Phrase;
import com.voronin.words.domain.Translation;
import com.voronin.words.domain.CardFilled;
import com.voronin.words.repository.WordRepository;
import com.voronin.words.util.PhrasesAndTranslationUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
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
    private WordRepository wordRepository = mock(WordRepository.class);

    /**
     * Mock PartOfSpeechService.
     */
    private PartOfSpeechService partOfSpeechService = mock(PartOfSpeechService.class);

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
     * The class object under test.
     */
    private WordService wordService =
            new WordService(
                    wordRepository,
                    partOfSpeechService,
                    translationService,
                    phraseService,
                    phrasesAndTranslation);

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
        partOfSpeech.setName("speech");
        word.setWord("word");
        word.setPartOfSpeech(partOfSpeech);
        cardFilled = new CardFilled("word", "transcription", "translation",
                "speech", "firstPhrase", "secondPhrase",
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

    /**
     * When call deleteWord method should call delete method
     * WordRepository class once and change numberOfWords in the PartOfSpeech.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenDeleteWordShouldChangeTheNumberOfWordsInThePartOfSpeech() throws Exception {
        final PartOfSpeech part = new PartOfSpeech();
        part.setNumberOfWords(2);
        final int expectedNumberOfWords = 1;
        this.word.setPartOfSpeech(part);
        when(this.wordRepository.getWordById(uuid)).thenReturn(word);

        this.wordService.deleteWord(uuid);

        assertThat(part.getNumberOfWords(), is(expectedNumberOfWords));
        verify(this.wordRepository, times(1)).delete(word);
        verify(this.partOfSpeechService, times(1)).save(part);
    }

    /**
     * When editWordAndSave without change part of speech
     * should return changed word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenEditWordAndSaveWithoutChangesPartOfSpeechShouldReturnSavedWord() throws Exception {
        when(this.wordService.getWordById(uuid)).thenReturn(word);
        this.word.setPhrases(new HashSet<>(Lists.newArrayList(new Phrase())));
        this.word.setTranslations(Lists.newArrayList(new Translation()));

        this.wordService.editWordAndSave(cardFilled, uuid.toString());

        assertThat(this.word.getDescription(), is(cardFilled.getDescription()));
        assertThat(this.word.getTranscription(), is(cardFilled.getTranscription()));
        assertThat(this.word.getPartOfSpeech().getName(), is(cardFilled.getPartOfSpeech()));
    }

    /**
     * When editWordAndSave with changed part of speech should return changed word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenEditWordAndSaveWithChangedPartOfSpeechShouldReturnChangedWord() throws Exception {
        this.word.setPhrases(new HashSet<>(Lists.newArrayList(new Phrase())));
        this.word.setTranslations(Lists.newArrayList(new Translation()));
        this.cardFilled.setPartOfSpeech("new part of speech");
        this.partOfSpeech.setNumberOfWords(2);
        final int oldPartOfSpeechNumberOfWords = 1;
        final int newPartOfSpeechNumberOfWords = 1;
        final PartOfSpeech part = new PartOfSpeech();
        part.setName("new part of speech");
        when(this.wordService.getWordById(uuid)).thenReturn(word);
        when(this.wordService.save(any(Word.class))).thenReturn(word);
        when(this.partOfSpeechService.getPartOfSpeechByName(cardFilled.getPartOfSpeech())).thenReturn(part);
        when(this.partOfSpeechService.save(any(PartOfSpeech.class))).thenReturn(part);

        this.wordService.editWordAndSave(cardFilled, uuid.toString());

        assertThat(this.word.getDescription(), is(cardFilled.getDescription()));
        assertThat(this.word.getTranscription(), is(cardFilled.getTranscription()));
        assertThat(word.getPartOfSpeech().getNumberOfWords(), is(newPartOfSpeechNumberOfWords));
        assertThat(this.partOfSpeech.getNumberOfWords(), is(oldPartOfSpeechNumberOfWords));
    }

    /**
     * When call getWordByWord should return Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetWordByWordShouldReturnNoun() throws Exception {
        when(wordRepository.getWordByWord(word.getWord())).thenReturn(word);

        assertThat(wordService.getWordByWord(word.getWord()), is(word));
        verify(wordRepository, times(1)).getWordByWord(word.getWord());
    }

    /**
     * When call getWordsByNames should return list of Word.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNounsByNamesShouldReturnListNouns() throws Exception {
        List<Word> words = Lists.newArrayList(word, word, word);
        List<String> listOfNames = Lists.newArrayList("word", "word", "word");
        when(wordRepository.getAllByWordIn(listOfNames)).thenReturn(words);

        assertThat(wordService.getWordByNames(listOfNames), is(words));
        verify(wordRepository, times(1)).getAllByWordIn(listOfNames);
    }
}
