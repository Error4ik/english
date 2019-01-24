package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

/**
 * Image.
 *
 * @author Alexey Voronin.
 * @since 14.09.2018.
 */
@Entity(name = "images")
public class Image {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Image name.
     */
    private String name;

    /**
     * Image url.
     */
    private String url;

    /**
     * Empty constructor.
     */
    public Image() {
    }

    /**
     * Constructor.
     *
     * @param name image name.
     * @param url  image url.
     */
    public Image(final String name, final String url) {
        this.name = name;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        Image image = (Image) o;
        return Objects.equals(getId(), image.getId())
                && Objects.equals(getName(), image.getName())
                && Objects.equals(getUrl(), image.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUrl());
    }

    @Override
    public String toString() {
        return String.format("Image {id=%s name=%s url=%s}", getId(), getName(), getUrl());
    }
}
