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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "BILLING_DETAILS_TYPE",
        discriminatorType = DiscriminatorType.STRING)
public abstract class AnyWord {

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
    public AnyWord() {
    }

    /**
     * Constructor.
     *
     * @param word          word.
     * @param transcription translation.
     * @param partOfSpeech  part of speech.
     * @param description   description.
     */
    public AnyWord(final String word,
                   final String transcription,
                   final PartOfSpeech partOfSpeech,
                   final String description) {
        this.word = word;
        this.transcription = transcription;
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
        if (!(o instanceof AnyWord)) {
            return false;
        }
        AnyWord word1 = (AnyWord) o;
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
