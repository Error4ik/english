package com.voronin.sentence.service;

import com.voronin.sentence.domain.Category;
import com.voronin.sentence.repository.CategoryRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CategoryService test class.
 *
 * @author Alexey Voronin.
 * @since 18.08.2019.
 */
public class CategoryServiceTest {

    /**
     * Mock CategoryRepository class.
     */
    private CategoryRepository categoryRepository = mock(CategoryRepository.class);

    /**
     * The class object under test.
     */
    private CategoryService categoryService = new CategoryService(categoryRepository);

    /**
     * When call save method should return saved category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedCategory() throws Exception {
        Category category = new Category();
        when(this.categoryRepository.save(category)).thenReturn(category);

        assertThat(this.categoryService.save(category), is(category));
    }

    /**
     * When call getCategories method should return list of Category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetCategoriesShouldReturnListOfCategory() throws Exception {
        final List<Category> categories = Lists.newArrayList(new Category(), new Category());
        when(this.categoryRepository.findAll()).thenReturn(categories);

        assertThat(this.categoryService.getCategories(), is(categories));
    }

    /**
     * When call getCategoryByName should return Category.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallGetCategoryByNameShouldReturnCategory() throws Exception {
        Category category = new Category();
        when(this.categoryRepository.getByName("category")).thenReturn(category);

        assertThat(this.categoryService.getCategoryByName("category"), is(category));
    }
}
