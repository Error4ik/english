package com.voronin.english.service;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Image service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class ImageService {

    /**
     * Image repository.
     */
    private final ImageRepository imageRepository;

    /**
     * Constructor.
     *
     * @param imageRepository image repository.
     */
    @Autowired
    public ImageService(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Get image by id.
     * @param id image id.
     * @return Image.
     */
    public Image getImageById(final UUID id) {
        return this.imageRepository.getOne(id);
    }

    /**
     * Save image.
     * @param image image.
     * @return Image.
     */
    public Image save(final Image image) {
        return this.imageRepository.save(image);
    }
}
