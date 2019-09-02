package com.voronin.words.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Not a noun word.
 *
 * @author Alexey Voronin.
 * @since 05.04.2019.
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
     * Part of speech.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_of_speech_id")
    private PartOfSpeech partOfSpeech;

    /**
     * Translations.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "word_id")
    private List<Translation> translations;

    /**
     * Phrases.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "word_id")
    private Set<Phrase> phrases;

    /**
     * Description.
     */
    private String description;

    /**
     * Create date.
     */
    @Column(name = "created")
    private Timestamp date;

    /**
     * Empty constructor.
     */
    public Word() {

    }

    /**
     * Constructor.
     *
     * @param word          any word.
     * @param transcription Transcription.
     * @param partOfSpeech  PartOfSpeech.
     * @param description   any description.
     */
    public Word(final String word,
                final String transcription,
                final PartOfSpeech partOfSpeech,
                final String description) {
        this.word = word;
        this.transcription = transcription;
        this.partOfSpeech = partOfSpeech;
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

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(final PartOfSpeech partOfSpeech) {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(final Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Word {id = %s, word = %s}", getId(), getWord());
    }
}
