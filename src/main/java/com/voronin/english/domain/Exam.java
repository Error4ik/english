package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Exam class.
 *
 * @author Alexey Voronin.
 * @since 07.12.2018.
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
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Exam type.
     */
    private int type;

    /**
     * Empty constructor.
     */
    public Exam() {
    }

    /**
     * Constructor.
     *
     * @param name     exam name.
     * @param category exam category.
     */
    public Exam(final String name, final Category category) {
        this.name = name;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exam)) {
            return false;
        }
        Exam exam = (Exam) o;
        return getType() == exam.getType()
                && Objects.equals(getId(), exam.getId())
                && Objects.equals(getName(), exam.getName())
                && Objects.equals(getQuestions(), exam.getQuestions())
                && Objects.equals(getCategory(), exam.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getQuestions(), getCategory(), getType());
    }

    @Override
    public String toString() {
        return String.format("Exam{id=%s, name=%s}", getId(), getName());
    }
}
