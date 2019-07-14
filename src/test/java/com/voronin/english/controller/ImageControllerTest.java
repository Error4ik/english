package com.voronin.english.controller;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import com.voronin.english.service.DetailService;
import com.voronin.english.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ImageController test class.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImageControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock ImageService.
     */
    @MockBean
    private ImageService imageService;

    /**
     * Mock Image.
     */
    @MockBean
    private Image image;

    /**
     * Mock ImageRepository.
     */
    @MockBean
    private ImageRepository imageRepository;

    /**
     * When mapping '/image/{id}' should call the getImageById method of the ImageService class once.
     *
     * @throws Exception exception.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenMappingImageByIdShouldReturnByteArray() throws Exception {
        final UUID id = UUID.randomUUID();

        this.mockMvc.perform(get("/image/{id}", id))
                .andExpect(status().isOk());

        verify(this.imageService, times(1)).getBytesFromImage(id);
    }
}
