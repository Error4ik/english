package com.voronin.sentence.service;

import com.voronin.sentence.domain.KeyWord;
import com.voronin.sentence.repository.KeyWordRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * KeyWordService Test.
 *
 * @author Alexey Voronin.
 * @since 11.10.2019.
 */
public class KeyWordServiceTest {

    /**
     * KeyWordRepository.
     */
    private final KeyWordRepository keyWordRepository = mock(KeyWordRepository.class);

    /**
     * The class object under test.
     */
    private KeyWordService keyWordService = new KeyWordService(keyWordRepository);

    /**
     * When call method save should return saved KeyWord.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveShouldReturnSavedKeyWord() throws Exception {
        final KeyWord word = new KeyWord(UUID.randomUUID(), "word");

        when(this.keyWordRepository.save(word)).thenReturn(word);

        assertThat(this.keyWordService.save(word), is(word));
    }

    /**
     * When call method getKeyWords should return list of KeyWords.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetKeyWordsShouldReturnListOfKeyWords() throws Exception {
        final List<KeyWord> list = Lists.newArrayList(new KeyWord(UUID.randomUUID(), "word"));

        when(this.keyWordRepository.findAll()).thenReturn(list);

        assertThat(this.keyWordService.getKeyWords(), is(list));
    }

    /**
     * When getKeyWordsIn should return list of KeyWords coincidences.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetKeyWordsInShouldReturnListOfKeyWordsCoincidences() throws Exception {
        final List<String> strings = Lists.newArrayList("word1", "word2");
        final List<KeyWord> keyWords = Lists.newArrayList(new KeyWord(UUID.randomUUID(), "word"));

        when(this.keyWordRepository.findByWordIn(strings)).thenReturn(keyWords);

        assertThat(this.keyWordService.getKeyWordsIn(strings), is(keyWords));
    }
}
