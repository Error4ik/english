package com.voronin.english.controller;

import com.voronin.english.domain.Image;
import com.voronin.english.repository.ImageRepository;
import com.voronin.english.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private Image image;

    @MockBean
    private ImageRepository imageRepository;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenMappingImageByIdShouldReturnByteArray() throws Exception {
        final UUID id = UUID.randomUUID();
        File file = Files.createTempFile("file", ".png").toFile();
        FileInputStream fis = new FileInputStream(file);

        when(this.imageService.getImageById(id)).thenReturn(image);
        when(this.image.getUrl()).thenReturn(file.getAbsolutePath());
        whenNew(File.class).withAnyArguments().thenReturn(file);
        whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);

        this.mockMvc.perform(get("/image/{id}", id))
                .andExpect(status().isOk());

        verify(imageService, times(1)).getImageById(id);
    }
}
