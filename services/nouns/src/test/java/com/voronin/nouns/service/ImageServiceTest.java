package com.voronin.nouns.service;

import com.voronin.nouns.domain.Image;
import com.voronin.nouns.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * ImageService test class.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
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

    /**
     * When call method getBytesFromImage with image
     * equals null should return byte array zero length.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetBytesFromImageWithImageEqualsNullShouldReturnByteArrayZeroLength() throws Exception {
        final UUID uuid = UUID.randomUUID();
        when(this.imageRepository.getOne(uuid)).thenReturn(null);
        byte[] b = new byte[0];

        assertThat(this.imageService.getBytesFromImage(uuid), is(b));
        verify(this.imageRepository, times(1)).getOne(uuid);
    }

    /**
     * When call getBytesFromImage with image, should return byte array.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetBytesFromImageWithImageShouldReturnByteArray() throws Exception {
        final UUID uuid = UUID.randomUUID();
        File file = File.createTempFile("test", ".txt");
        image.setUrl(file.getAbsolutePath());
        when(this.imageRepository.getOne(uuid)).thenReturn(image);
        String text = "Hello world!";
        byte[] b;
        try (FileOutputStream fos = new FileOutputStream(file); FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
            b = IOUtils.toByteArray(fis);
        }

        assertThat(this.imageService.getBytesFromImage(uuid), is(b));
        verify(this.imageRepository, times(1)).getOne(uuid);
        file.delete();
    }

    /**
     * When call delete method should call delete method ImageRepository class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenDeleteImageShouldCallDeleteMethodOneTime() throws Exception {
        this.imageService.delete(image);

        verify(this.imageRepository, times(1)).delete(image);
    }
}