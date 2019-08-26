package com.voronin.sentence.domain;

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
@Entity(name = "types")
public class Type {

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
    @Column(name = "type")
    private String type;

    /**
     * Constructor.
     *
     * @param type TypeOfPhrase.
     */
    public Type(final String type) {
        this.type = type;
    }

    /**
     * Constructor.
     */
    public Type() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        Type type1 = (Type) o;
        return Objects.equals(getId(), type1.getId())
                && Objects.equals(getType(), type1.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType());
    }

    @Override
    public String toString() {
        return String.format("Type {id=%s, type=%s}", getId(), getType());
    }
}
