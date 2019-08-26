package com.voronin.nouns.controller;

import com.voronin.nouns.domain.Image;
import com.voronin.nouns.repository.ImageRepository;
import com.voronin.nouns.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ImageController test class.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ImageController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
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
