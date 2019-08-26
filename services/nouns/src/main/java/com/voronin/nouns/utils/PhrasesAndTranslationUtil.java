package com.voronin.nouns.utils;

import com.google.common.collect.Lists;
import com.voronin.nouns.domain.CardFilled;
import com.voronin.nouns.domain.Noun;
import com.voronin.nouns.domain.Phrase;
import com.voronin.nouns.domain.Translation;
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
     * @param card Filled model of the noun.
     * @param noun noun.
     * @return List of Phrase.
     */
    public List<Phrase> getPhrases(final CardFilled card, final Noun noun) {
        Phrase p1 = new Phrase(card.getFirstPhrase(), card.getFirstPhraseTranslation(), noun);
        Phrase p2 = new Phrase(card.getSecondPhrase(), card.getSecondPhraseTranslation(), noun);
        return Lists.newArrayList(p1, p2);
    }

    /**
     * Get translations.
     *
     * @param card Filled model of the noun.
     * @param noun noun.
     * @return list of Translation.
     */
    public List<Translation> getTranslation(final CardFilled card, final Noun noun) {
        List<Translation> translations = new ArrayList<>();
        for (String s : card.getTranslation().split(",")) {
            Translation t = new Translation(s.trim(), noun);
            translations.add(t);
        }
        return translations;
    }

    /**
     * Update phrases for noun.
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
