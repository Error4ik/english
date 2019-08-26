package com.voronin.words.util;

import com.google.common.collect.Lists;
import com.voronin.words.domain.CardFilled;
import com.voronin.words.domain.Phrase;
import com.voronin.words.domain.Translation;
import com.voronin.words.domain.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 02.07.2019.
 */
@Component
public class PhrasesAndTranslationUtil {

    /**
     * Get phrases.
     *
     * @param card Filled model of the word.
     * @param word word.
     * @return List of Phrase.
     */
    public List<Phrase> getPhrases(final CardFilled card, final Word word) {
        Phrase p1 = new Phrase(card.getFirstPhrase(), card.getFirstPhraseTranslation(), word);
        Phrase p2 = new Phrase(card.getSecondPhrase(), card.getSecondPhraseTranslation(), word);
        return Lists.newArrayList(p1, p2);
    }

    /**
     * Get translations.
     *
     * @param card Filled model of the word.
     * @param word word.
     * @return list of Translation.
     */
    public List<Translation> getTranslation(final CardFilled card, final Word word) {
        List<Translation> translations = new ArrayList<>();
        for (String s : card.getTranslation().split(",")) {
            Translation t = new Translation(s.trim(), word);
            translations.add(t);
        }
        return translations;
    }

    /**
     * Update phrases for word.
     *
     * @param oldPhrases List of old phrases.
     * @param newPhrases list of new Phrases.
     */
    public void updatePhrase(final List<Phrase> oldPhrases, final List<Phrase> newPhrases) {
        int index = 0;
        for (Phrase phrase : oldPhrases) {
            phrase.setPhrase(newPhrases.get(index).getPhrase());
            phrase.setTranslate(newPhrases.get(index).getTranslate());
            index++;
        }
    }
}
