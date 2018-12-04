package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.domain.Image;
import com.voronin.english.repository.CategoryRepository;
import com.voronin.english.util.WriteFileToDisk;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CategoryService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private WriteFileToDisk writeFileToDisk;
    @MockBean
    private ImageService imageService;
    @MockBean
    private MultipartFile multipartFile;
    @MockBean
    private File file;

    private Category category = new Category();
    private Image image = new Image();
    @Value("${upload.image.category.folder}")
    private String pathToSaveImage;

    @Before
    public void init() throws Exception {
        image.setName("image");
        image.setUrl("path");
        category.setName("test");
        category.setId(UUID.randomUUID());
        category.setDescription("test desc");
        category.setImage(this.image);
    }

    @Autowired
    private CategoryService categoryService;

    @Test
    public void whenCallGetCategoriesShouldReturnListCategories() throws Exception {
        List<Category> list = new ArrayList<>();
        list.add(category);

        when(categoryRepository.findAll()).thenReturn(list);

        assertThat(categoryService.getCategories(), is(list));
    }

    @Test
    public void whenGetCategoryByNameShouldReturnOneCategory() throws Exception {
        when(categoryRepository.getCategoryByName(category.getName())).thenReturn(category);

        assertThat(categoryService.getCategoryByName(category.getName()).getId(), is(category.getId()));
    }

    @Test
    public void whenPrepareAndSaveShouldReturnCategory() throws Exception {
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage)).thenReturn(file);
        when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoryService.prepareAndSave(category, multipartFile), is(category));
    }

    @Test
    public void whenSaveCategoryShouldReturnCategory() throws Exception {
        when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoryService.save(category), is(category));
    }
}
