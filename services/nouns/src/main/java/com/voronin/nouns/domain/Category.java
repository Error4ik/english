package com.voronin.nouns.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.Objects;
import java.util.UUID;

/**
 * Category class.
 *
 * @author Alexey Voronin.
 * @since 17.09.2018.
 */
@Entity(name = "categories")
public class Category {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Category name.
     */
    private String name;

    /**
     * Category description.
     */
    private String description;

    /**
     * Category image.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    /**
     * Number of nouns in the category.
     */
    @JoinColumn(name = "nouns_count")
    private int nounsCount;

    /**
     * Empty constructor.
     */
    public Category() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public int getNounsCount() {
        return nounsCount;
    }

    public void setNounsCount(final int nounsCount) {
        this.nounsCount = nounsCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId())
                && Objects.equals(getName(), category.getName())
                && Objects.equals(getDescription(), category.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return String.format("Category {id=%s name=%s description=%s}", getId(), getName(), getDescription());
    }
}
