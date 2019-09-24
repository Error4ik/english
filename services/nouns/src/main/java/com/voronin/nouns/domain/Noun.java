package com.voronin.nouns.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Noun.
 *
 * @author Alexey Voronin.
 * @since 05.04.2019.
 */
@Entity(name = "nouns")
public class Noun {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Noun.
     */
    private String word;

    /**
     * Transcription.
     */
    private String transcription;

    /**
     * Part of speech noun.
     */
    @Column(name = "part_of_speech")
    private String partOfSpeech;

    /**
     * Category.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Image.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id")
    private Image image;

    /**
     * Translations.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "noun_id")
    private List<Translation> translations;

    /**
     * Phrases.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "noun_id")
    private Set<Phrase> phrases;

    /**
     * Description.
     */
    private String description;

    /**
     * Create created.
     */
    @Column(name = "created")
    private Timestamp created;

    /**
     * Empty constructor.
     */
    public Noun() {

    }

    /**
     * Constructor.
     *
     * @param word          word.
     * @param transcription transcription.
     * @param partOfSpeech  part of speech.
     * @param category      category.
     * @param description   description
     */
    public Noun(
            final String word,
            final String transcription,
            final String partOfSpeech,
            final Category category,
            final String description) {
        this.word = word;
        this.transcription = transcription;
        this.partOfSpeech = partOfSpeech;
        this.category = category;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(final List<Translation> translations) {
        this.translations = translations;
    }

    public Set<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(final Set<Phrase> phrases) {
        this.phrases = phrases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(final Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format("Noun {id = %s, word = %s}", getId(), getWord());
    }
}
