package com.voronin.english.service;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * ImageService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ImageService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class ImageServiceTest {

    /**
     * Mock ImageRepository.
     */
    @MockBean
    private ImageRepository imageRepository;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * initialization of objects for the tests.
     */
    @Autowired
    private ImageService imageService;

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
    }

    /**
     * When call getImageById should return image.
     */
    @Test
    public void whenGetImageByIdShouldReturnImage() {
        when(imageRepository.getOne(image.getId())).thenReturn(image);

        assertThat(this.imageService.getImageById(image.getId()), is(image));
    }
}
