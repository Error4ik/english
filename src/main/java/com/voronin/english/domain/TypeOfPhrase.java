package com.voronin.english.domain;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import java.util.Objects;
import java.util.UUID;

/**
 * The type phrase, the question of approval or denial.
 *
 * @author Alexey Voronin.
 * @since 01.02.2019.
 */
@Entity(name = "type_of_phrase")
public class TypeOfPhrase {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Type of phrase.
     */
    @Column(name = "type_of_phrase")
    private String typeOfPhrase;

    /**
     * Constructor.
     *
     * @param typeOfPhrase TypeOfPhrase.
     */
    public TypeOfPhrase(final String typeOfPhrase) {
        this.typeOfPhrase = typeOfPhrase;
    }

    /**
     * Constructor.
     */
    public TypeOfPhrase() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getTypeOfPhrase() {
        return typeOfPhrase;
    }

    public void setTypeOfPhrase(final String typeOfPhrase) {
        this.typeOfPhrase = typeOfPhrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfPhrase)) {
            return false;
        }
        TypeOfPhrase that = (TypeOfPhrase) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getTypeOfPhrase(), that.getTypeOfPhrase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTypeOfPhrase());
    }

    @Override
    public String toString() {
        return String.format("TypeOfPhrase{id=%s, typeOfPhrase=%s}", getId(), getTypeOfPhrase());
    }
}
