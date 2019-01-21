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
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WordService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class WordServiceTest {

    @Autowired
    private WordService wordService;

    @MockBean
    private WriteFileToDisk writeFileToDisk;
    @MockBean
    private WordRepository wordRepository;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private PartOfSpeechService partOfSpeechService;
    @MockBean
    private ImageService imageService;
    @MockBean
    private TranslationService translationService;
    @MockBean
    private PhraseService phraseService;
    @MockBean
    private MultipartFile multipartFile;
    @MockBean
    private File file;

    @Value("${upload.image.folder}")
    private String pathToSaveImage;

    private Word word = new Word();
    private Category category = new Category();
    private PartOfSpeech partOfSpeech = new PartOfSpeech();
    private UUID uuid = UUID.randomUUID();
    private CardFilled cardFilled;
    private Image image = new Image();
    private List<Word> list = new ArrayList<>();

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

    @Test
    public void whenGetWordsShouldReturnListWords() throws Exception {
        when(wordRepository.findAll()).thenReturn(list);

        assertThat(wordService.getWords(), is(list));
    }

    @Test
    public void whenGetWordsByCategoryShouldReturnListWords() throws Exception {
        when(wordRepository.getAllByCategoryId(category.getId())).thenReturn(list);

        assertThat(wordService.getWordsByCategory(category.getId()), is(list));
    }

    @Test
    public void whenPrepareAndSaveWithPhotoShouldReturnWord() throws Exception {
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage)).thenReturn(file);
        when(categoryService.getCategoryByName(cardFilled.getCategory())).thenReturn(category);
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);

        assertThat(wordService.prepareAndSave(cardFilled, multipartFile).getWord(), is(word.getWord()));
    }

    @Test
    public void whenPrepareAndSaveWithoutPhotoShouldReturnWord() throws Exception {
        when(partOfSpeechService.getPartOfSpeechByName(anyString())).thenReturn(partOfSpeech);

        Word w = wordService.prepareAndSave(cardFilled, null);
        assertThat(w.getWord(), is(word.getWord()));
    }

    @Test
    public void whenGetWordByNameShouldReturnWord() throws Exception {
        when(wordRepository.getWordByWord(word.getWord())).thenReturn(word);

        assertThat(wordService.getWordByName(word.getWord()), is(word));
    }

    @Test
    public void whenGetWordByIdShouldReturnWord() throws Exception {
        when(wordRepository.getWordById(word.getId())).thenReturn(word);

        assertThat(wordService.getWordById(word.getId()), is(word));
    }

    @Test
    public void whenGetWordByNamesShouldReturnListWord() throws Exception {
        List<Word> words = Lists.newArrayList(word, word, word);
        List<String> list = Lists.newArrayList("word", "word", "word");
        when(wordRepository.getAllByWordIn(list)).thenReturn(words);

        assertThat(wordService.getWordsByNames(list), is(words));
    }

    @Test
    public void whenGetWordsByPartOfSpeechShouldReturnListWord() throws Exception {
        when(partOfSpeechService.getById(uuid)).thenReturn(partOfSpeech);
        when(wordRepository.getAllByPartOfSpeech(partOfSpeech)).thenReturn(list);

        assertThat(wordService.getWordsByPartOfSpeech(uuid), is(list));
    }
}
