package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

/**
 * Word is a noun.
 *
 * @author Alexey Voronin.
 * @since 05.04.2019.
 */
@Entity
@DiscriminatorValue("noun")
public class Noun extends AnyWord {

    /**
     * Category.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Image.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    /**
     * Empty constructor.
     */
    public Noun() {

    }

    /**
     * Constructor.
     *
     * @param word          any word.
     * @param transcription Transcription.
     * @param partOfSpeech  PartOfSpeech.
     * @param category      category.
     * @param description   any description.
     */
    public Noun(
            final String word,
            final String transcription,
            final PartOfSpeech partOfSpeech,
            final Category category,
            final String description) {
        super(word, transcription, partOfSpeech, description);
        this.category = category;
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

    @Override
    public String toString() {
        return String.format("%s category=%s, image=%s", super.toString(), getCategory(), getImage());
    }
}
