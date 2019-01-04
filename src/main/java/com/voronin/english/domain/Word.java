package com.voronin.english.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 17.09.2018.
 */
@Entity(name = "words")
public class Word {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String word;

    private String transcription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_of_speech_id")
    private PartOfSpeech partOfSpeech;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private List<Translation> translations;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private Set<Phrase> phrases;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    private String description;

    @Column(name = "date_added")
    private Timestamp date;

    public Word() {
    }

    public Word(String word,
                String transcription,
                Category category,
                PartOfSpeech partOfSpeech,
                String description) {
        this.word = word;
        this.transcription = transcription;
        this.category = category;
        this.partOfSpeech = partOfSpeech;
        this.description = description;
        this.date = Timestamp.valueOf(LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public Set<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(Set<Phrase> phrases) {
        this.phrases = phrases;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(getId(), word1.getId()) &&
                Objects.equals(getWord(), word1.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWord());
    }

    @Override
    public String toString() {
        return String.format("Word {id=%s word=%s}",
                getId(), getWord());
    }
}
