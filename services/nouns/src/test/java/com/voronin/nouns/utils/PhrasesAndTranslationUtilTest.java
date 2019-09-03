package com.voronin.nouns.utils;

import com.voronin.nouns.domain.CardFilled;
import com.voronin.nouns.domain.Noun;
import com.voronin.nouns.domain.Phrase;
import com.voronin.nouns.domain.Translation;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * PhrasesAndTranslationUtil test class.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
 */
public class PhrasesAndTranslationUtilTest {

    /**
     * Noun class.
     */
    private Noun noun;

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
        noun = new Noun();
        noun.setId(UUID.randomUUID());
        cardFilled = new CardFilled("word",
                "transcription",
                "translation one, translation two",
                "category",
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
                new Phrase(cardFilled.getFirstPhrase(), cardFilled.getFirstPhraseTranslation(), noun),
                new Phrase(cardFilled.getSecondPhrase(), cardFilled.getSecondPhraseTranslation(), noun));

        List<Phrase> phrases = this.phrasesAndTranslationUtil.getPhrases(cardFilled, noun);

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
                new Translation("translation one", noun),
                new Translation("translation two", noun));

        List<Translation> translation = this.phrasesAndTranslationUtil.getTranslation(cardFilled, noun);

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
                new Phrase("first phrase", "first translate", noun),
                new Phrase("second phrase", "second translate", noun));
        List<Phrase> newPhrasesList = Lists.newArrayList(
                new Phrase("firstPhrase", "firstPhraseTranslation", noun),
                new Phrase("secondPhrase", "secondPhraseTranslation", noun));

        this.phrasesAndTranslationUtil.updatePhrase(oldPhraseList, newPhrasesList);

        assertThat(oldPhraseList, is(newPhrasesList));
    }

}
