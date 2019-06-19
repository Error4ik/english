package com.voronin.english.controller;

import com.voronin.english.service.CategoryService;
import com.voronin.english.service.PhraseCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CategoryController test.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock CategoryService.
     */
    @MockBean
    private CategoryService categoryService;

    /**
     * Mock PhraseCategoryService.
     */
    @MockBean
    private PhraseCategoryService phraseCategoryService;

    /**
     * When mapping '/category/categories' should call the getCategories method of the CategoryService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingCategoriesShouldReturnStatusOkAndOneCallGetCategoriesMethod() throws Exception {
        this.mockMvc
                .perform(get("/category/categories"))
                .andExpect(status().isOk());

        verify(this.categoryService, times(1)).getCategories();
    }

    /**
     * When mapping '/category/phrase-category' should call the getPhraseCategories method of
     * the PhraseCategoryService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingPhraseCategoryShouldReturnStatusOkAndCallGetPhraseCategoriesMethod() throws Exception {
        this.mockMvc
                .perform(get("/category/phrase-category"))
                .andExpect(status().isOk());

        verify(this.phraseCategoryService, times(1)).findAll();
    }
}
