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
 * CategoryService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CategoryService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class CategoryServiceTest {

    /**
     * Mock CategoryRepository.
     */
    @MockBean
    private CategoryRepository categoryRepository;

    /**
     * Mock WriteFileToDisk.
     */
    @MockBean
    private WriteFileToDisk writeFileToDisk;

    /**
     * Mock ImageService.
     */
    @MockBean
    private ImageService imageService;

    /**
     * Mock MultipartFile.
     */
    @MockBean
    private MultipartFile multipartFile;

    /**
     * Mock File.
     */
    @MockBean
    private File file;

    /**
     * Category for test.
     */
    private Category category = new Category();

    /**
     * Image for test.
     */
    private Image image = new Image();

    /**
     * Path to save image.
     */
    @Value("${upload.image.category.folder}")
    private String pathToSaveImage;

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        image.setName("image");
        image.setUrl("path");
        category.setName("test");
        category.setId(UUID.randomUUID());
        category.setDescription("test desc");
        category.setImage(this.image);
    }

    /**
     * The class object under test.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * When call getCategories method should return list of categories.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallGetCategoriesShouldReturnListCategories() throws Exception {
        List<Category> list = new ArrayList<>();
        list.add(category);

        when(categoryRepository.findAllByOrderByNameAsc()).thenReturn(list);

        assertThat(categoryService.getCategories(), is(list));
    }

    /**
     * When call getCategoryByName method with parameter should return one category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetCategoryByNameShouldReturnOneCategory() throws Exception {
        when(categoryRepository.getCategoryByName(category.getName())).thenReturn(category);

        assertThat(categoryService.getCategoryByName(category.getName()).getId(), is(category.getId()));
    }

    /**
     * When call prepareAndSave method should return saved category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnCategory() throws Exception {
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage)).thenReturn(file);
        when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoryService.prepareAndSave(category, multipartFile), is(category));
    }

    /**
     * When call save method should return saved category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveCategoryShouldReturnCategory() throws Exception {
        when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoryService.save(category), is(category));
    }
}
