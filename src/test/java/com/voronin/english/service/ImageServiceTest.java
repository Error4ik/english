package com.voronin.english.service;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * ImageService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class ImageServiceTest {

    /**
     * Mock ImageRepository.
     */
    private ImageRepository imageRepository = mock(ImageRepository.class);

    /**
     * initialization of objects for the tests.
     */
    private ImageService imageService = new ImageService(imageRepository);

    /**
     * Class for test.
     */
    private final Image image = new Image("image", "url");

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        this.image.setId(UUID.randomUUID());
    }

    /**
     * When call save should return saved image.
     */
    @Test
    public void whenSaveImageShouldReturnImage() {
        when(imageRepository.save(image)).thenReturn(image);

        assertThat(this.imageService.save(image), is(image));
        verify(imageRepository, times(1)).save(image);
    }

    /**
     * When call getImageById should return image.
     */
    @Test
    public void whenGetImageByIdShouldReturnImage() {
        when(imageRepository.getOne(image.getId())).thenReturn(image);

        assertThat(this.imageService.getImageById(image.getId()), is(image));
        verify(imageRepository, times(1)).getOne(image.getId());
    }
}
