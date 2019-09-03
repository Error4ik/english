package com.voronin.nouns.service;

import com.voronin.nouns.domain.Category;
import com.voronin.nouns.domain.Image;
import com.voronin.nouns.repository.CategoryRepository;
import com.voronin.nouns.utils.WriteFileToDisk;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * CategoryService test class.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
 */
public class CategoryServiceTest {

    /**
     * Mock CategoryRepository.
     */
    private CategoryRepository categoryRepository = mock(CategoryRepository.class);

    /**
     * Mock WriteFileToDisk.
     */
    private WriteFileToDisk writeFileToDisk = mock(WriteFileToDisk.class);

    /**
     * Mock ImageService.
     */
    private ImageService imageService = mock(ImageService.class);

    /**
     * Mock MultipartFile.
     */
    private MultipartFile multipartFile = mock(MultipartFile.class);

    /**
     * Mock File.
     */
    private File file = mock(File.class);

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
    private CategoryService categoryService = new CategoryService(categoryRepository, writeFileToDisk, imageService);

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
        verify(categoryRepository, times(1)).findAllByOrderByNameAsc();
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
        verify(categoryRepository, times(1)).getCategoryByName(category.getName());
    }

    /**
     * When call prepareAndSave method should return saved category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenPrepareAndSaveShouldReturnCategory() throws Exception {
        when(writeFileToDisk.writeImage(multipartFile, pathToSaveImage, category.getName())).thenReturn(file);
        when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoryService.prepareAndSave(category, multipartFile), is(category));
        verify(categoryRepository, times(1)).save(category);
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
        verify(categoryRepository, times(1)).save(category);
    }

}
