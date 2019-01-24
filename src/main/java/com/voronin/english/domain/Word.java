package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * Word.
 *
 * @author Alexey Voronin.
 * @since 17.09.2018.
 */
@Entity(name = "words")
public class Word {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Word.
     */
    private String word;

    /**
     * Transcription.
     */
    private String transcription;

    /**
     * Category.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Part of speech.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_of_speech_id")
    private PartOfSpeech partOfSpeech;

    /**
     * Translations.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private List<Translation> translations;

    /**
     * Phrases.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private Set<Phrase> phrases;

    /**
     * Image.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    /**
     * Description.
     */
    private String description;

    /**
     * Create date.
     */
    @Column(name = "date_added")
    private Timestamp date;

    /**
     * Empty constructor.
     */
    public Word() {
    }

    /**
     * Constructor.
     *
     * @param word          word.
     * @param transcription translation.
     * @param category      category.
     * @param partOfSpeech  part of speech.
     * @param description   description.
     */
    public Word(final String word,
                final String transcription,
                final Category category,
                final PartOfSpeech partOfSpeech,
                final String description) {
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Word)) {
            return false;
        }
        Word word1 = (Word) o;
        return Objects.equals(getId(), word1.getId())
                && Objects.equals(getWord(), word1.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWord());
    }

    @Override
    public String toString() {
        return String.format("Word {id=%s word=%s}", getId(), getWord());
    }
}
