package com.voronin.english.controller;

import com.voronin.english.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * Mapping web requests /image/{imageId}.
     *
     * @param id id image to be search in the database.
     * @return byte array.
     * @throws IOException if you can not get an array of bytes from the photo.
     */
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable final UUID id) throws IOException {
        byte[] b;
        try (FileInputStream fis = new FileInputStream(new File(this.imageService.getImageById(id).getUrl()))) {
            b = IOUtils.toByteArray(fis);
        }
        return b;
    }
}
