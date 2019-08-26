package com.voronin.nouns.domain;

/**
 * Object to fill the card.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
public class CardFilled {

    /**
     * Noun.
     */
    private String noun;

    /**
     * Transcription.
     */
    private String transcription;

    /**
     * Translation.
     */
    private String translation;

    /**
     * Category.
     */
    private String category;

    /**
     * Part of speech.
     */
    private String partOfSpeech;

    /**
     * First phrase.
     */
    private String firstPhrase;

    /**
     * Second phrase.
     */
    private String secondPhrase;

    /**
     * First phrase translate.
     */
    private String firstPhraseTranslation;

    /**
     * Second phrase translate.
     */
    private String secondPhraseTranslation;

    /**
     * Description.
     */
    private String description;

    /**
     * Constructor.
     *
     * @param noun                    noun.
     * @param transcription           transcription.
     * @param translation             translation.
     * @param category                category.
     * @param partOfSpeech            part of speech.
     * @param firstPhrase             first phrase.
     * @param secondPhrase            second phrase.
     * @param firstPhraseTranslation  first phrase translation.
     * @param secondPhraseTranslation second phrase translation.
     * @param description             description.
     */
    public CardFilled(
            final String noun,
            final String transcription,
            final String translation,
            final String category,
            final String partOfSpeech,
            final String firstPhrase,
            final String secondPhrase,
            final String firstPhraseTranslation,
            final String secondPhraseTranslation,
            final String description) {
        this.noun = noun;
        this.transcription = transcription;
        this.translation = translation;
        this.category = category;
        this.partOfSpeech = partOfSpeech;
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.firstPhraseTranslation = firstPhraseTranslation;
        this.secondPhraseTranslation = secondPhraseTranslation;
        this.description = description;
    }

    /**
     * Empty constructor.
     */
    public CardFilled() {
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(final String noun) {
        this.noun = noun;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(final String transcription) {
        this.transcription = transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(final String translation) {
        this.translation = translation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getFirstPhrase() {
        return firstPhrase;
    }

    public void setFirstPhrase(final String firstPhrase) {
        this.firstPhrase = firstPhrase;
    }

    public String getSecondPhrase() {
        return secondPhrase;
    }

    public void setSecondPhrase(final String secondPhrase) {
        this.secondPhrase = secondPhrase;
    }

    public String getFirstPhraseTranslation() {
        return firstPhraseTranslation;
    }

    public void setFirstPhraseTranslation(final String firstPhraseTranslation) {
        this.firstPhraseTranslation = firstPhraseTranslation;
    }

    public String getSecondPhraseTranslation() {
        return secondPhraseTranslation;
    }

    public void setSecondPhraseTranslation(final String secondPhraseTranslation) {
        this.secondPhraseTranslation = secondPhraseTranslation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CardFilled{"
                + "noun='" + noun + '\''
                + ", transcription='" + transcription + '\''
                + ", translation='" + translation + '\''
                + ", category='" + category + '\''
                + ", partOfSpeech='" + partOfSpeech + '\''
                + ", firstPhrase='" + firstPhrase + '\''
                + ", secondPhrase='" + secondPhrase + '\''
                + ", firstPhraseTranslation='" + firstPhraseTranslation + '\''
                + ", secondPhraseTranslation='" + secondPhraseTranslation + '\''
                + ", description='" + description + '\''
                + '}';
    }
}
