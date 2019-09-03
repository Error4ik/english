package com.voronin.words.util;

import com.voronin.words.domain.Word;
import com.voronin.words.domain.CardFilled;
import com.voronin.words.domain.Phrase;
import com.voronin.words.domain.Translation;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * @author Alexey Voronin.
 * @since 14.07.2019.
 */
public class PhrasesAndTranslationUtilTest {

    /**
     * AnyWord class.
     */
    private Word word;

    /**
     *
     */
    private CardFilled cardFilled;

    /**
     *
     */
    private PhrasesAndTranslationUtil phrasesAndTranslationUtil = new PhrasesAndTranslationUtil();

    /**
     *
     */
    @Before
    public void init() {
        word = new Word();
        word.setId(UUID.randomUUID());
        cardFilled = new CardFilled("word",
                "transcription",
                "translation one, translation two",
                "speech",
                "firstPhrase",
                "secondPhrase",
                "firstPhraseTranslation",
                "secondPhraseTranslation",
                "description");
    }

    /**
     * When getPhrases should return list of phrases.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetPhrasesShouldReturnListOfPhrases() throws Exception {
        List<Phrase> expectedListOfPhrase = Lists.newArrayList(
                new Phrase(cardFilled.getFirstPhrase(), cardFilled.getFirstPhraseTranslation(), word),
                new Phrase(cardFilled.getSecondPhrase(), cardFilled.getSecondPhraseTranslation(), word));

        List<Phrase> phrases = this.phrasesAndTranslationUtil.getPhrases(cardFilled, word);

        assertThat(phrases, is(expectedListOfPhrase));
    }

    /**
     * When getTranslation should return list of translation.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetTranslationShouldReturnListOfTranslation() throws Exception {
        List<Translation> expectedListOfTranslation = Lists.newArrayList(
                new Translation("translation one", word),
                new Translation("translation two", word));

        List<Translation> translation = this.phrasesAndTranslationUtil.getTranslation(cardFilled, word);

        assertThat(translation, is(expectedListOfTranslation));
    }

    /**
     * When updatePhrase should change the old phrase list to the new one.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUpdatePhraseShouldReturnChangedListOfPhrase() throws Exception {
        List<Phrase> oldPhraseList = Lists.newArrayList(
                new Phrase("first phrase", "first translate", word),
                new Phrase("second phrase", "second translate", word));
        List<Phrase> newPhrasesList = Lists.newArrayList(
                new Phrase("firstPhrase", "firstPhraseTranslation", word),
                new Phrase("secondPhrase", "secondPhraseTranslation", word));

        this.phrasesAndTranslationUtil.updatePhrase(oldPhraseList, newPhrasesList);

        assertThat(oldPhraseList, is(newPhrasesList));
    }
}
