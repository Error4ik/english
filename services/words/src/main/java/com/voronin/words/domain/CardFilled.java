package com.voronin.words.domain;

/**
 * Object to fill the card.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
public class CardFilled {

    /**
     * Word.
     */
    private String word;

    /**
     * Transcription.
     */
    private String transcription;

    /**
     * Translation.
     */
    private String translation;

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
     * @param word                    word.
     * @param transcription           transcription.
     * @param translation             translation.
     * @param partOfSpeech            part of speech.
     * @param firstPhrase             first phrase.
     * @param secondPhrase            second phrase.
     * @param firstPhraseTranslation  first phrase translation.
     * @param secondPhraseTranslation second phrase translation.
     * @param description             description.
     */
    public CardFilled(
            final String word,
            final String transcription,
            final String translation,
            final String partOfSpeech,
            final String firstPhrase,
            final String secondPhrase,
            final String firstPhraseTranslation,
            final String secondPhraseTranslation,
            final String description) {
        this.word = word;
        this.transcription = transcription;
        this.translation = translation;
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

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
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
                + "word='" + word + '\''
                + ", transcription='" + transcription + '\''
                + ", translation='" + translation + '\''
                + ", partOfSpeech='" + partOfSpeech + '\''
                + ", firstPhrase='" + firstPhrase + '\''
                + ", secondPhrase='" + secondPhrase + '\''
                + ", firstPhraseTranslation='" + firstPhraseTranslation + '\''
                + ", secondPhraseTranslation='" + secondPhraseTranslation + '\''
                + ", description='" + description + '\''
                + '}';
    }
}
