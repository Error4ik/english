package com.voronin.english.controller;

import com.voronin.english.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Image controller.
 *
 * @author Alexey Voronin.
 * @since 23.11.2018.
 */
@RestController
public class ImageController {

    /**
     * Image service.
     */
    private final ImageService imageService;

    /**
     * Constructor.
     *
     * @param imageService image service.
     */
    @Autowired
    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Mapping web requests /image/{imageId}.
     *
     * @param id id image to be search in the database.
     * @return byte array.
     */
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable final UUID id) {
        return this.imageService.getBytesFromImage(id);
    }
}
