package com.voronin.english.service;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image getImageById(final UUID id) {
        return this.imageRepository.getOne(id);
    }

    public Image save(final Image image) {
        return this.imageRepository.save(image);
    }
}
