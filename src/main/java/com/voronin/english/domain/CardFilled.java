package com.voronin.english.domain;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
public class CardFilled {

    private String word;

    private String transcription;

    private String translation;

    private String category;

    private String partOfSpeech;

    private String firstPhrase;

    private String secondPhrase;

    private String firstPhraseTranslation;

    private String secondPhraseTranslation;

    private String description;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getFirstPhrase() {
        return firstPhrase;
    }

    public void setFirstPhrase(String firstPhrase) {
        this.firstPhrase = firstPhrase;
    }

    public String getSecondPhrase() {
        return secondPhrase;
    }

    public void setSecondPhrase(String secondPhrase) {
        this.secondPhrase = secondPhrase;
    }

    public String getFirstPhraseTranslation() {
        return firstPhraseTranslation;
    }

    public void setFirstPhraseTranslation(String firstPhraseTranslation) {
        this.firstPhraseTranslation = firstPhraseTranslation;
    }

    public String getSecondPhraseTranslation() {
        return secondPhraseTranslation;
    }

    public void setSecondPhraseTranslation(String secondPhraseTranslation) {
        this.secondPhraseTranslation = secondPhraseTranslation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CardFilled{" +
                "word='" + word + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translation='" + translation + '\'' +
                ", category='" + category + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", firstPhrase='" + firstPhrase + '\'' +
                ", secondPhrase='" + secondPhrase + '\'' +
                ", firstPhraseTranslation='" + firstPhraseTranslation + '\'' +
                ", secondPhraseTranslation='" + secondPhraseTranslation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
