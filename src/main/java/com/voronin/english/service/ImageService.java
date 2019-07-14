package com.voronin.english.service;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ImageService.class);

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
     *
     * @param id image id.
     * @return Image.
     */
    public Image getImageById(final UUID id) {
        return this.imageRepository.getOne(id);
    }

    /**
     * Save image.
     *
     * @param image image.
     * @return Image.
     */
    public Image save(final Image image) {
        return this.imageRepository.save(image);
    }

    /**
     * To get the image in bytes.
     *
     * @param imageId image id.
     * @return image in bytes.
     */
    public byte[] getBytesFromImage(final UUID imageId) {
        byte[] b = new byte[0];
        Image image = this.getImageById(imageId);
        if (image != null) {
            try (FileInputStream fis = new FileInputStream(new File(image.getUrl()))) {
                b = IOUtils.toByteArray(fis);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return b;
    }

    /**
     * Delete image.
     *
     * @param image image for delete.
     */
    public void delete(final Image image) {
        this.imageRepository.delete(image);
    }
}
