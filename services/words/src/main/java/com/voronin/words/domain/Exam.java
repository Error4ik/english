package com.voronin.words.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.UUID;

import java.util.List;

/**
 * Exam by word.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@Entity(name = "exams")
public class Exam {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Exam name.
     */
    @Column(name = "name")
    private String name;

    /**
     * List of questions in the exam.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private List<Question> questions;

    /**
     * Exam category.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_of_speech_id")
    private PartOfSpeech partOfSpeech;

    /**
     * Exam type.
     */
    private int type;

    /**
     * Constructor.
     */
    public Exam() {
    }

    /**
     * Constructor.
     *
     * @param name         Exam name.
     * @param partOfSpeech part of speech.
     * @param type         Exam type.
     */
    public Exam(final String name, final PartOfSpeech partOfSpeech, final int type) {
        this.name = name;
        this.partOfSpeech = partOfSpeech;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Exam {id=%s, name=%s}", getId(), getName());
    }
}
